package com.example.interfaceonduleurv0.onduleur;

import com.example.interfaceonduleurv0.Controller;
import com.example.interfaceonduleurv0.RPI.ModeleQPIGS;
import com.example.interfaceonduleurv0.RPI.ModeleQPIRI;
import com.example.interfaceonduleurv0.RPI.ModeleQPIWS;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;


public class Wks extends LiaisonSerie {
    //truc
Controller ctrl ;
    public Wks(Controller ctrl) throws SQLException {
        this.ctrl = ctrl;
    }

    private final byte[] QPIGS = "QPIGS".getBytes(StandardCharsets.US_ASCII);
    private final byte[] QPIRI = "QPIRI".getBytes(StandardCharsets.US_ASCII);
    private final byte[] QPIWS = "QPIWS".getBytes(StandardCharsets.US_ASCII);
    //private final byte START = 0x28;
    private final byte CR = 0x0d;
    ModeleQPIGS qpigs = new ModeleQPIGS();
    ModeleQPIRI qpiri = new ModeleQPIRI();
    ModeleQPIWS qpiws = new ModeleQPIWS();
    byte[] trameBrute;
    String st_trameBrute;
    String[] dcp;

    public ModeleQPIGS qpigsModel() throws SQLException {
        System.out.println("decodage de QPIGS");
        trameBrute = new byte[110];

        trameBrute = super.lireTrame(110);
        st_trameBrute = new String(trameBrute, StandardCharsets.US_ASCII);
        dcp = st_trameBrute.split(" ");
        System.out.println("Qpigs = " + dcp.length);


        System.out.println("Methode QPIGS");
        System.out.println(st_trameBrute);

        System.out.println("""
                %s V = TensionDuReseau 0
                %s Hz = FrequenceDuReseau 1
                %s V = TensionDeSortie_AC 2
                %s Hz = FrequenceDeSortie_AC 3
                %s VA = PuissanceApparenteDeSortie_AC 4
                %s W = PuissanceActiveDeSortie_AC 5
                %s A = CourantEntreePv
                %s V = TensionBUS 7
                %s V = TensionBatterie 8
                %s A = CourantRechargeBatterie 9
                %s /100 = PourcentageChargeSortie 6
                %s �C = TemperatureDuRadiateurOnduleur 10
                %s V = TensionEntreePv 12
                %s V = TentionBatterie 13
                %s A = CourantDechargeBatterie 14
                %s = StatusMateriel1 15 (
                %s mV = TensionVentilateurs_10mv 16
                %s = VersionEEPROM 17
                %s W = PuissanceChargePv 18
                """.formatted(dcp[0].replace('(', ' '), dcp[1], dcp[2], dcp[3], dcp[4], dcp[5], dcp[6], dcp[7], dcp[8], dcp[9], dcp[10], dcp[11], dcp[12], dcp[13], dcp[14], dcp[15], dcp[16], dcp[17], dcp[18], dcp[19]));

        qpigs.setTensionDuReseau(dcp[3]);
        qpigs.setFrequenceDuReseau(dcp[1]);
        qpigs.setTensionDeSortie_AC(dcp[2]);
        qpigs.setFrequenceDeSortie_AC(dcp[3]);
        qpigs.setPuissanceApparenteDeSortie_AC(dcp[4]);
        qpigs.setPuissanceActiveDeSortie_AC(dcp[5]);
        qpigs.setPourcentageChargeSortie(dcp[10]);
        qpigs.setTensionBUS(dcp[7]);
        qpigs.setTensionBatterie(dcp[8]);
        qpigs.setCourantRechargeBatterie(dcp[9]);
        qpigs.setPourcentageCapaciteBatterie(dcp[11]);
        qpigs.setTemperatureDuRadiateurOnduleur(dcp[12]);
        qpigs.setCourantEntreePv(dcp[13]);
        qpigs.setTensionEntreePv(dcp[14]);
        qpigs.setTensionBatterieSCC(dcp[15]);
        qpigs.setCourantDechargeBatterie(dcp[16]);
        qpigs.setStatusMateriel1(dcp[17]);
        qpigs.setTensionVentilateurs_10mv(dcp[18]);
        qpigs.setVersionEEPROM(dcp[19]);
        qpigs.setPuissanceChargePv(dcp[19]);
        return qpigs;
    }
    public ModeleQPIRI qpiriModel() {
        trameBrute = new byte[104];
        trameBrute = super.lireTrame(104);
        if (trameBrute.length == 104) {
            st_trameBrute = new String(trameBrute, StandardCharsets.US_ASCII);
            dcp = st_trameBrute.split(" ");
            System.out.println("Qpiri = " + dcp.length);
//
            System.out.println("Methode QPIRI");
            System.out.println(st_trameBrute);
            System.out.println("""
                            %s V = tension nominale du r�seau
                            %s A = courant nominale du r�seau
                            %s V = TensionNominaleDeSortie_AC
                            %s Hz = FrequenceNominaleDeSortie_AC
                            %s A = CourantNominalDeSortie_AC
                            %s VA = PuissanceApparenteDeSortie_AC
                            %s W = PuissanceActiveDeSortie_AC
                            %s V =TensionNominaleBatterie
                            %s V = TensionRechargeBatterie
                            %s V = TensionBasseBatterie
                            %s V = TensionMasseBatterie
                            %s V = TensionEntretienBatterie
                            %s = TypeBatterie
                            %s A = CourantDeCharge_AC_max
                            %s A = CourantDeCharge_max
                            %s = PlageDeTensionEntree
                            %s = PrioriteSourceDeSortie
                            %S = PrioriteSourceDuChargeur
                            """.formatted(dcp[0].replace('(', ' '), dcp[1], dcp[2], dcp[3], dcp[4], dcp[5], dcp[6], dcp[7], dcp[8], dcp[9], dcp[10], dcp[11], dcp[12], dcp[13], dcp[14], dcp[15], dcp[16], dcp[17]));

            qpiri.setTensionNominaleDuReseau(dcp[0]);
            qpiri.setCourantNominalDuReseau(dcp[1]);
            qpiri.setTensionNominaleDeSortie_AC(dcp[2]);
            qpiri.setFrequenceNominaleDeSortie_AC(dcp[3]);
            qpiri.setCourantNominalDeSortie_AC(dcp[4]);
            qpiri.setPuissanceApparenteDeSortie_AC(dcp[5]);
            qpiri.setPuissanceActiveDeSortie_AC(dcp[6]);
            qpiri.setTensionNominaleBatterie(dcp[7]);
            qpiri.setTensionRechargeBatterie(dcp[8]);
            qpiri.setTensionBasseBatterie(dcp[9]);
            qpiri.setTensionMasseBatterie(dcp[10]);
            qpiri.setTensionEntretienBatterie(dcp[11]);
            qpiri.setTypeBatterie(dcp[12]);
            qpiri.setCourantDeCharge_AC_max(dcp[13]);
            qpiri.setCourantDeCharge_max(dcp[14]);
            qpiri.setPlageDeTensionEntree(dcp[15]);
            qpiri.setPrioriteSourceDeSortie(dcp[16]);
            qpiri.setPrioriteSourceDuChargeur(dcp[17]);
        }
        return qpiri;
    }
    public ModeleQPIWS qpiwsModel(){
        trameBrute = new byte[40];
        trameBrute = super.lireTrame(40);
        if (trameBrute.length == 40) {
            st_trameBrute = new String(trameBrute, StandardCharsets.US_ASCII);
            dcp = st_trameBrute.split(" ");
            System.out.println("Qpiws = " + +dcp.length);
            System.out.println("Methode QPIWS");
            System.out.println(st_trameBrute);
            System.out.println("""
                            %s = Reserved
                            %s = Default onduleur
                            %s = Bus termin�
                            %s = Bus Under
                            %s = EchecProgressifBus
                            %s = �chec de ligne
                            %s = OPVShort
                            %s = TensionOnduleurTropFaible
                            %s = TensionOnduleurTropElevee
                            %s = Surchauffe
                            %s = VentilateurVerrouille
                            %s = TensionBatterieElevee
                            %s = AlarmeBatterieFaible
                            %s = Reserve_Surcharge
                            %s = BatterieArret
                            %s = ReserveDeclassementBatterie
                            %s = Surcharge
                            %s = DefautEeprom
                            %s = SurintensiteOnduleur
                            %s = OnduleurSoftFail
                            %s = EchecAuto_test
                            %s = TensionContinueOPsur
                            %s = BatOpen
                            %s = DefaillanceCapteurCourant
                            %s = Batteriecourte
                            %s = LimitePuissance
                            %s = TensionElevee1
                            %s = DefautSurchargeMPPT1
                            %s = AvertissementSurchargeMPPT1
                            %s = BatterieTropFaiblePourEtreChargee1
                            %s = TensionPVelevee2
                            %s = DefautSurchargeMPPT2
                            %s = AvertissementSurchargeMPPT2
                            %s = BatterieTropFaiblePourCharger2
                            %s = TensionPVelevee3
                            %s = DefautSurchargeMPPT3
                            %s = AvertissementSurchargeMPPT3
                            %s = BatterieTropFaiblePourEtreChargee3
                            """.formatted(dcp[0].toCharArray()[0], dcp[0].toCharArray()[1], dcp[0].toCharArray()[2], dcp[0].toCharArray()[3], dcp[0].toCharArray()[4],
                    dcp[0].toCharArray()[5], dcp[0].toCharArray()[6], dcp[0].toCharArray()[7], dcp[0].toCharArray()[8], dcp[0].toCharArray()[9], dcp[0].toCharArray()[10],
                    dcp[0].toCharArray()[11], dcp[0].toCharArray()[12], dcp[0].toCharArray()[13], dcp[0].toCharArray()[14], dcp[0].toCharArray()[15], dcp[0].toCharArray()[16],
                    dcp[0].toCharArray()[17], dcp[0].toCharArray()[18], dcp[0].toCharArray()[19], dcp[0].toCharArray()[20], dcp[0].toCharArray()[21], dcp[0].toCharArray()[22],
                    dcp[0].toCharArray()[23], dcp[0].toCharArray()[24], dcp[0].toCharArray()[25], dcp[0].toCharArray()[26], dcp[0].toCharArray()[27], dcp[0].toCharArray()[28],
                    dcp[0].toCharArray()[29], dcp[0].toCharArray()[30], dcp[0].toCharArray()[31], dcp[0].toCharArray()[32], dcp[0].toCharArray()[33], dcp[0].toCharArray()[34],
                    dcp[0].toCharArray()[35], dcp[0].toCharArray()[36], dcp[0].toCharArray()[37]));
            qpiws.setReserved(String.valueOf(dcp[0].toCharArray()[0]));
            qpiws.setDefaillanceOnduleur(String.valueOf(dcp[0].toCharArray()[1]));
            qpiws.setBusOver(String.valueOf(dcp[0].toCharArray()[2]));
            qpiws.setBusSous(String.valueOf(dcp[0].toCharArray()[3]));
            qpiws.setEchecProgressifBus(String.valueOf(dcp[0].toCharArray()[4]));
            qpiws.setLineFail(String.valueOf(dcp[0].toCharArray()[5]));
            qpiws.setoPVShort(String.valueOf(dcp[0].toCharArray()[6]));
            qpiws.setTensionOnduleurTropFaible(String.valueOf(dcp[0].toCharArray()[7]));
            qpiws.setTensionOnduleurTropElevee(String.valueOf(dcp[0].toCharArray()[8]));
            qpiws.setSurchauffe(String.valueOf(dcp[0].toCharArray()[9]));
            qpiws.setVentilateurVerrouille(String.valueOf(dcp[0].toCharArray()[10]));
            qpiws.setTensionBatterieElevee(String.valueOf(dcp[0].toCharArray()[11]));
            qpiws.setAlarmeBatterieFaible(String.valueOf(dcp[0].toCharArray()[12]));
            qpiws.setReserve_Surcharge(String.valueOf(dcp[0].toCharArray()[13]));
            qpiws.setBatterieArret(String.valueOf(dcp[0].toCharArray()[14]));
            qpiws.setReserveDeclassementBatterie(String.valueOf(dcp[0].toCharArray()[15]));
            qpiws.setSurcharge(String.valueOf(dcp[0].toCharArray()[16]));
            qpiws.setDefautEeprom(String.valueOf(dcp[0].toCharArray()[17]));
            qpiws.setSurintensiteOnduleur(String.valueOf(dcp[0].toCharArray()[18]));
            qpiws.setOnduleurSoftFail(String.valueOf(dcp[0].toCharArray()[19]));
            qpiws.setEchecAuto_test(String.valueOf(dcp[0].toCharArray()[20]));
            qpiws.setTensionContinueOPsur(String.valueOf(dcp[0].toCharArray()[21]));
            qpiws.setBatOpen(String.valueOf(dcp[0].toCharArray()[22]));
            qpiws.setDefaillanceCapteurCourant(String.valueOf(dcp[0].toCharArray()[23]));
            qpiws.setBatteriecourte(String.valueOf(dcp[0].toCharArray()[24]));
            qpiws.setLimitePuissance(String.valueOf(dcp[0].toCharArray()[25]));
            qpiws.setTensionElevee1(String.valueOf(dcp[0].toCharArray()[26]));
            qpiws.setDefautSurchargeMPPT1(String.valueOf(dcp[0].toCharArray()[27]));
            qpiws.setAvertissementSurchargeMPPT1(String.valueOf(dcp[0].toCharArray()[28]));
            qpiws.setBatterieTropFaiblePourEtreChargee1(String.valueOf(dcp[0].toCharArray()[29]));
            qpiws.setTensionPVelevee2(String.valueOf(dcp[0].toCharArray()[30]));
            qpiws.setDefautSurchargeMPPT2(String.valueOf(dcp[0].toCharArray()[31]));
            qpiws.setAvertissementSurchargeMPPT2(String.valueOf(dcp[0].toCharArray()[32]));
            qpiws.setBatterieTropFaiblePourCharger2(String.valueOf(dcp[0].toCharArray()[33]));
            qpiws.setTensionPVelevee3(String.valueOf(dcp[0].toCharArray()[34]));
            qpiws.setDefautSurchargeMPPT3(String.valueOf(dcp[0].toCharArray()[35]));
            qpiws.setAvertissementSurchargeMPPT3(String.valueOf(dcp[0].toCharArray()[36]));
            qpiws.setBatterieTropFaiblePourEtreChargee3(String.valueOf(dcp[0].toCharArray()[37]));
        }
       return qpiws;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        try {
            //
            Thread.sleep(750);
            System.out.println("nbs d 'octets : " + serialPort.getInputBufferBytesCount());
            //QPIGS
            if (serialPort.getInputBufferBytesCount() == 110) {
                qpigsModel();
            }
            //QPIRI
            if (serialPort.getInputBufferBytesCount() == 104) {
                Thread.sleep(750);
                qpiriModel();
            }
            //QPIWS
            if (serialPort.getInputBufferBytesCount() == 40) {
                Thread.sleep(750);
                qpiwsModel();
            }
        } catch (SerialPortException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ModeleQPIGS demandeQPIGS() {
        System.out.println("demandeQPIGS");
        StringBuilder sb = new StringBuilder();
        byte[] crc = intToByteArray(crc16CcittXmodel(QPIGS));
        for (byte b : ArrayUtils.add(ArrayUtils.addAll(QPIGS, crc), CR)) {
            sb.append(String.format("%02X ", b));
        }
        System.out.println("Demande (hexa)  -> " + sb);
        System.out.println("Demande (acsii) -> " + new String(ArrayUtils.add(ArrayUtils.addAll(QPIGS, crc), CR)));
        super.ecrire(ArrayUtils.add(ArrayUtils.addAll(QPIGS, crc), CR));
        return qpigs;
    }

    public void demandeQPIRI() {
        System.out.println("demandeQPIRI");
        StringBuilder sb = new StringBuilder();
        byte[] crc = intToByteArray(crc16CcittXmodel(QPIRI));
        for (byte b : ArrayUtils.add(ArrayUtils.addAll(QPIRI, crc), CR)) {
            sb.append(String.format("%02X ", b));
        }

        System.out.println("Demande (hexa)  -> " + sb);
        System.out.println("Demande (acsii) -> " + new String(ArrayUtils.add(ArrayUtils.addAll(QPIRI, crc), CR)));
        super.ecrire(ArrayUtils.add(ArrayUtils.addAll(QPIRI, crc), CR));
    }

    public void demandeQPIWS() {
        StringBuilder sb = new StringBuilder();
        byte[] crc = intToByteArray(crc16CcittXmodel(QPIWS));
        for (byte b : ArrayUtils.add(ArrayUtils.addAll(QPIWS, crc), CR)) {
            sb.append(String.format("%02X ", b));
        }

        System.out.println("Demande (hexa)  -> " + sb);
        System.out.println("Demande (acsii) -> " + new String(ArrayUtils.add(ArrayUtils.addAll(QPIWS, crc), CR)));
        super.ecrire(ArrayUtils.add(ArrayUtils.addAll(QPIWS, crc), CR));
    }

    public int crc16CcittXmodel(byte[] bytes) {
        int crc = 0x0000;          // initial value=0x0000 (CCITT Xmodem) initial value=0x0000 (CCITT)
        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)
        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        //System.out.println("CRC16-CCITT = " + Integer.toHexString(crc));
        return crc;
    }

    public byte[] intToByteArray(int value) {
        return new byte[]{(byte) (value >>> 8), (byte) value};
    }

//    public static int hex_to_int(String s) {
//        String digits = "0123456789ABCDEF";
//        s = s.toUpperCase();
//        int val = 0;
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            int d = digits.indexOf(c);
//            val = 16 * val + d;
//        }
//        return val;
//    }
}

