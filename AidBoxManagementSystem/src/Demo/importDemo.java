/*
 * Nome: Agostinho Manuel da Silva Ferreira
 * Número: 8210590
 * Turma: LEI11T1
 *
 * Nome: Henrique Miguel Gomes da Silva
 * Número: 8230097
 * Turma: LEI11T1
 */

package Demo;

import Import.ImportAPI;
import coreImpl.AidBoxImpl;

public class importDemo {
    
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        ImportAPI importAPI = new ImportAPI();
        AidBoxImpl[] aidBoxes = importAPI.getAidBoxArray();

        for (int i = 0; i < aidBoxes.length; i++) {
            System.out.println(aidBoxes[i].toString());
        }

        try{
            System.out.println("Distancia entre a AidBox[0] e AidBox[1]: " + aidBoxes[0].getDistance(aidBoxes[1]));
        } catch (Exception e) {
            System.out.println("Problem occurred!" + e);
        }
    }
}