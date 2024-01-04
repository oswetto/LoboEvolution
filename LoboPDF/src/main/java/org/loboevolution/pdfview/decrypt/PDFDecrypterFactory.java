/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.pdfview.decrypt;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Produces a {@link org.loboevolution.pdfview.decrypt.PDFDecrypter} for documents given a (possibly non-existent)
 * Encrypt dictionary. Supports decryption of versions 1, 2 and 4 of the
 * password-based encryption mechanisms as described in PDF Reference version
 * 1.7. This means that it supports RC4 and AES encryption with keys of
 * 40-128 bits; esentially, password-protected documents with compatibility
 * up to Acrobat 8.
 * <p>
 * See "PDF Reference version 1.7, section 3.5: Encryption"
 * Author Luke Kirby
 */
@Slf4j
public class PDFDecrypterFactory {

    /**
     * The name of the standard Identity CryptFilter
     */
    public static final String CF_IDENTITY = "Identity";

    /**
     * Default key length for versions where key length is optional
     */
    private static final int DEFAULT_KEY_LENGTH = 40;

    /**
     * Create a decryptor for a given encryption dictionary. A check is
     * immediately performed that the supplied password decrypts content
     * described by the encryption specification.
     *
     * @param encryptDict the Encrypt dict as found in the document's trailer.
     *                    May be null, in which case the {@link org.loboevolution.pdfview.decrypt.IdentityDecrypter} will
     *                    be returned.
     * @param documentId  the object with key "ID" in the trailer's dictionary.
     *                    Should always be present if Encrypt is.
     * @param password    the password to use; may be <code>null</code>
     * @return The decryptor that should be used for all encrypted data in the
     * PDF
     * @throws java.io.IOException                                                        if any.
     * @throws org.loboevolution.pdfview.decrypt.EncryptionUnsupportedByPlatformException if any.
     * @throws org.loboevolution.pdfview.decrypt.EncryptionUnsupportedByProductException  if any.
     * @throws org.loboevolution.pdfview.decrypt.PDFAuthenticationFailureException        if any.
     */
    public static PDFDecrypter createDecryptor
    (final PDFObject encryptDict, final PDFObject documentId, PDFPassword password)
            throws
            IOException,
            EncryptionUnsupportedByPlatformException,
            EncryptionUnsupportedByProductException,
            PDFAuthenticationFailureException {

        // none of the classes beyond us want to see a null PDFPassword
        password = PDFPassword.nonNullPassword(password);

        if (encryptDict == null) {
            // No encryption specified
            return IdentityDecrypter.getInstance();
        } else {
            final PDFObject filter = encryptDict.getDictRef("Filter");
            // this means that we'll fail if, for example, public key
            // encryption is employed
            if (filter != null && "Standard".equals(filter.getStringValue())) {
                final PDFObject vObj = encryptDict.getDictRef("V");
                final int v = vObj != null ? vObj.getIntValue() : 0;
                if (v == 1 || v == 2) {
                    final PDFObject lengthObj =
                            encryptDict.getDictRef("Length");
                    final Integer length =
                            lengthObj != null ? lengthObj.getIntValue() : null;
                    return createStandardDecrypter(
                            encryptDict, documentId, password, length, false,
                            StandardDecrypter.EncryptionAlgorithm.RC4);
                } else if (v == 4) {
                    return createCryptFilterDecrypter(
                            encryptDict, documentId, password, v);
                } else {
                    throw new EncryptionUnsupportedByPlatformException(
                            "Unsupported encryption version: " + v);
                }
            } else if (filter == null) {
                throw new PDFParseException(
                        "No Filter specified in Encrypt dictionary");
            } else {
                throw new EncryptionUnsupportedByPlatformException(
                        "Unsupported encryption Filter: " + filter +
                                "; only Standard is supported.");
            }
        }
    }

    /**
     * Create a decrypter working from a crypt filter dictionary, as in
     * version 4 encryption
     *
     * @param encryptDict the Encrypt dictionary
     * @param documentId  the document ID
     * @param password    the provided password
     * @param v           the version of encryption being used; must be at least 4
     * @return the decrypter corresponding to the scheme expressed in
     * encryptDict
     * @throws PDFAuthenticationFailureException        if the provided password
     *                                                  does not decrypt this document
     * @throws IOException                              if there is a problem reading the PDF, an invalid
     *                                                  document structure, or an inability to obtain the required ciphers
     *                                                  from the platform's JCE
     * @throws EncryptionUnsupportedByPlatformException if the encryption
     *                                                  is not supported by the environment in which the code is executing
     * @throws EncryptionUnsupportedByProductException  if PDFRenderer does
     *                                                  not currently support the specified encryption
     */
    private static PDFDecrypter createCryptFilterDecrypter(
            final PDFObject encryptDict,
            final PDFObject documentId,
            final PDFPassword password,
            final int v)
            throws
            PDFAuthenticationFailureException,
            IOException,
            EncryptionUnsupportedByPlatformException,
            EncryptionUnsupportedByProductException {

        assert v >= 4 : "crypt filter decrypter not supported for " +
                "standard encryption prior to version 4";

        // encryptMetadata is true if not present. Note that we don't actually
        // use this to change our reading of metadata streams (that's all done
        // internally by the document specifying a Crypt filter of None if
        // appropriate), but it does affect the encryption key.
        boolean encryptMetadata = true;
        final PDFObject encryptMetadataObj =
                encryptDict.getDictRef("EncryptMetadata");
        if (encryptMetadataObj != null
                && encryptMetadataObj.getType() == PDFObject.BOOLEAN) {
            encryptMetadata = encryptMetadataObj.getBooleanValue();
        }

        final PDFObject bitLengthObj = encryptDict.getDictRef("Length");

        // Assemble decrypters for each filter in the
        // crypt filter (CF) dictionary
        final Map<String, PDFDecrypter> cfDecrypters =
                new HashMap<>();
        final PDFObject cfDict = encryptDict.getDictRef("CF");
        if (cfDict == null) {
            throw new PDFParseException(
                    "No CF value present in Encrypt dict for V4 encryption");
        }
        final Iterator<String> cfNameIt = cfDict.getDictKeys();
        while (cfNameIt.hasNext()) {
            final String cfName = cfNameIt.next();
            final PDFObject cryptFilter = cfDict.getDictRef(cfName);

            final PDFObject lengthObj = cryptFilter.getDictRef("Length");
            // The Errata for PDF 1.7 explains that the value of
            // Length in CF dictionaries is in bytes
            final Integer length = lengthObj != null ? lengthObj.getIntValue() * 8 :
                    (bitLengthObj != null) ? bitLengthObj.getIntValue() : null;

            // CFM is the crypt filter method, describing whether RC4,
            // AES, or None (i.e., identity) is the encryption mechanism
            // used for the name crypt filter
            final PDFObject cfmObj = cryptFilter.getDictRef("CFM");
            final String cfm = cfmObj != null ?
                    cfmObj.getStringValue() : "None";
            final PDFDecrypter cfDecrypter;
            if ("None".equals(cfm)) {
                cfDecrypter = IdentityDecrypter.getInstance();
            } else if ("V2".equals(cfm)) {
                cfDecrypter = createStandardDecrypter(
                        encryptDict, documentId, password, length,
                        encryptMetadata,
                        StandardDecrypter.EncryptionAlgorithm.RC4);
            } else if ("AESV2".equals(cfm)) {
                cfDecrypter = createStandardDecrypter(
                        encryptDict, documentId, password, length,
                        encryptMetadata,
                        StandardDecrypter.EncryptionAlgorithm.AESV2);
            } else {
                throw new UnsupportedOperationException(
                        "Unknown CryptFilter method: " + cfm);
            }
            cfDecrypters.put(cfName, cfDecrypter);
        }

        // always put Identity in last so that it will override any
        // Identity filter sneakily declared in the CF entry
        cfDecrypters.put(CF_IDENTITY, IdentityDecrypter.getInstance());

        final PDFObject stmFObj = encryptDict.getDictRef("StmF");
        final String defaultStreamFilter =
                stmFObj != null ? stmFObj.getStringValue() : CF_IDENTITY;

        final PDFObject strFObj = encryptDict.getDictRef("StrF");
        final String defaultStringFilter =
                strFObj != null ? strFObj.getStringValue() : CF_IDENTITY;

        return new CryptFilterDecrypter(
                cfDecrypters, defaultStreamFilter, defaultStringFilter);

    }

    /**
     * Create a standard single-algorithm AES or RC4 decrypter. The Encrypt
     * dictionary is used where possible, but where different encryption
     * versions employ different mechanisms of specifying configuration or may
     * be specified via a CF entry (e.g. key length), the value is specified as
     * a parameter.
     *
     * @param encryptDict          the Encrypt dictionary
     * @param documentId           the document ID
     * @param password             the password
     * @param keyLength            the key length, in bits; may be <code>null</code>
     *                             to use a {@link #DEFAULT_KEY_LENGTH default}
     * @param encryptMetadata      whether metadata is being encrypted
     * @param encryptionAlgorithm, the encryption algorithm
     * @return the decrypter
     * @throws PDFAuthenticationFailureException        if the provided password
     *                                                  is not the one expressed by the encryption dictionary
     * @throws IOException                              if there is a problem reading the PDF content,
     *                                                  if the content does not comply with the PDF specification
     * @throws EncryptionUnsupportedByPlatformException if the encryption
     *                                                  is not supported by the environment in which the code is executing
     * @throws EncryptionUnsupportedByProductException  if PDFRenderer does
     *                                                  not currently support the specified encryption
     */
    private static PDFDecrypter createStandardDecrypter(
            final PDFObject encryptDict,
            final PDFObject documentId,
            final PDFPassword password,
            Integer keyLength,
            final boolean encryptMetadata,
            final StandardDecrypter.EncryptionAlgorithm encryptionAlgorithm)
            throws
            PDFAuthenticationFailureException,
            IOException,
            EncryptionUnsupportedByPlatformException,
            EncryptionUnsupportedByProductException {

        if (keyLength == null) {
            keyLength = DEFAULT_KEY_LENGTH;
        }

        // R describes the revision of the security handler
        final PDFObject rObj = encryptDict.getDictRef("R");
        if (rObj == null) {
            throw new PDFParseException(
                    "No R entry present in Encrypt dictionary");
        }

        final int revision = rObj.getIntValue();
        if (revision < 2 || revision > 4) {
            throw new EncryptionUnsupportedByPlatformException(
                    "Unsupported Standard security handler revision; R=" +
                            revision);
        }

        // O describes validation details for the owner key
        final PDFObject oObj = encryptDict.getDictRef("O");
        if (oObj == null) {
            throw new PDFParseException(
                    "No O entry present in Encrypt dictionary");
        }
        final byte[] o = oObj.getStream();
        if (o.length != 32) {
            throw new PDFParseException("Expected owner key O " +
                    "value of 32 bytes; found " + o.length);
        }

        // U describes validation details for the user key
        final PDFObject uObj = encryptDict.getDictRef("U");
        if (uObj == null) {
            throw new PDFParseException(
                    "No U entry present in Encrypt dictionary");
        }
        final byte[] u = uObj.getStream();
        if (u.length != 32) {
            throw new PDFParseException(
                    "Expected user key U value of 32 bytes; found " + o.length);
        }

        // P describes the permissions regarding document usage
        final PDFObject pObj = encryptDict.getDictRef("P");
        if (pObj == null) {
            throw new PDFParseException(
                    "Required P entry in Encrypt dictionary not found");
        }

        return new StandardDecrypter(
                encryptionAlgorithm, documentId, keyLength,
                revision, o, u, pObj.getIntValue(), encryptMetadata, password);
    }

    /**
     * <p>isFilterExist.</p>
     *
     * @param encryptDict the Encrypt dict as found in the document's trailer.
     * @return true if the Filter exist in the current dictionary
     */
    public static boolean isFilterExist(final PDFObject encryptDict) {
        if (encryptDict != null) {
            try {
                final PDFObject filter = encryptDict.getDictRef("Filter");
                return filter != null;
            } catch (final IOException e) {
                log.info(e.getMessage());
            }
        }
        return false;
    }

}
