package burp.aes;

import burp.BurpExtender;
import burp.IIntruderPayloadProcessor;
import burp.utils.EncryptionType;

public class AesIntruderPayloadProcessor implements IIntruderPayloadProcessor {
    private BurpExtender parent;
    private final String extName;
    private final AesUtil AesUtil;

    public AesIntruderPayloadProcessor(final BurpExtender newParent, String extName, AesConfig config) {
        this.parent = newParent;
        this.extName = extName;
        AesUtil = new AesUtil();
        AesUtil.setConfig(config);
    }

    @Override
    public String getProcessorName() {
        return "BurpCrypto - AES Encrypt - " + extName;
    }

    @Override
    public byte[] processPayload(final byte[] currentPayload, final byte[] originalPayload, final byte[] baseValue) {
        try {
            byte[] result = AesUtil.encrypt(currentPayload).getBytes("UTF-8");
            parent.dict.Log(result, originalPayload);
            return result;
        } catch (Exception e) {
            this.parent.callbacks.issueAlert(e.toString());
            return null;
        }
    }
}
