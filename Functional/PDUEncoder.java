package Functional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDUEncoder {
    private final String GSM_7BIT_ALPHABET = "@£$¥èéùìòÇ\nØø\nÅåΔ_ΦΓΛΩΠΨΣΘΞ\u001bÆæßÉ !\"#¤%&'()*+,-./0123456789:;<=>?" +
            "¡ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÑÜ§¿abcdefghijklmnopqrstuvwxyzäöñüà";
    private int alphabet = 0;

    public String encodePdu(String phoneNumber, String message) {
        StringBuilder pduBuilder = new StringBuilder();

        String encodedMessage = encodeMessage(message);

        int messageLength = encodedMessage.length();

        String encodedPhoneNumber = encodePhoneNumber(phoneNumber);
        String PDUHeader = "";
        PDUHeader += "81";
        PDUHeader += encodePhoneNumber("871231980");
        pduBuilder.append(String.format("%02X", PDUHeader.length()/2));
        pduBuilder.append(PDUHeader);

        pduBuilder.append("04");
        pduBuilder.append(String.format("%02X", encodedPhoneNumber.length()));
        pduBuilder.append("C9");
        pduBuilder.append(encodedPhoneNumber);
        pduBuilder.append("00");
        pduBuilder.append(tpdcs_Making(message));
        pduBuilder.append("00");
        pduBuilder.append(alphabet == 1 ? String.format("%02X", messageLength/2) :String.format("%02X", messageLength));
        pduBuilder.append(encodedMessage);

        return pduBuilder.toString();
    }

    private String encodeMessage(String message) {
        StringBuilder encodedMessageBuilder = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);

            int index = GSM_7BIT_ALPHABET.indexOf(c);
            if (index != -1) {
                encodedMessageBuilder.append(String.format("%02X", index));
            } else {
                encodedMessageBuilder.append(String.format("%02X", 0x1B));
                encodedMessageBuilder.append(String.format("%02X", (int) c));
                alphabet = 1;
            }
        }

        return encodedMessageBuilder.toString();
    }

    private String encodePhoneNumber(String phoneNumber) {
        StringBuilder encodedNumberBuilder = new StringBuilder();

        if (phoneNumber.startsWith("+")) {
            phoneNumber = phoneNumber.substring(1);
        }

        for (int i = 0; i < phoneNumber.length(); i+=2) {
            if (i == phoneNumber.length() - 1){
                encodedNumberBuilder.append("F");
                encodedNumberBuilder.append(phoneNumber.charAt(i));
            }else {
                encodedNumberBuilder.append(phoneNumber.charAt(i+1));
                encodedNumberBuilder.append(phoneNumber.charAt(i));
            }
        }

        return encodedNumberBuilder.toString();
    }

    private String tpdcs_Making(String message){
        String tpdcs = "";
        tpdcs += "00";
        tpdcs += "0";
        tpdcs += "1";
        tpdcs += "0" + (alphabet == 1 ? "1" : "0");
        tpdcs += "11";

        tpdcs = Integer.toHexString(Integer.parseInt(tpdcs, 2));
        return tpdcs;
    }

    public static void writePduToFile(List<Sender> senders) {
        try (FileOutputStream fos = new FileOutputStream("messages.bin")) {
            for (Sender sender: senders) {
                byte[] pduBytes = hexStringToByteArray(sender.getMessage().getText());
                fos.write(sender.getSentMessages());
                fos.write(pduBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] byteArray = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }

        return byteArray;
    }
}
