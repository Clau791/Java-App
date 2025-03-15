import java.util.HashMap;
import java.util.Vector;
// pentru a aduce toate rezervarile din baza de date si a verifica disponibilitatea
public class PersonService {
    
    private static int convertToMinutes(String ora) {
        String[] parts = ora.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        return h * 60 + m;
}
        public boolean validateInterval(int ID, int zi, String interval){
            
            RezervareService rs = RezervareService.getInstance();

            // contine toate rezervarile salii
            HashMap<Integer,Vector< String> > data = rs.RezervariSala(ID);

            if(data.containsKey(zi)){
                for(String r : data.get(zi)){

                // ore stocate
                String intervalStocat = r;

                String[] ore = intervalStocat.split("-");
                int startD = convertToMinutes(ore[0]); // 13:00 → 780 minute
                int endD = convertToMinutes(ore[1]);   // 14:30 → 900 minute
        
                // (ex: 12:00 - 13:00)
                String intervalNou = interval;
                String[] ore_i = intervalNou.split("-");
                int startI = convertToMinutes(ore_i[0]); // 12:00 → 720 minute
                int endI = convertToMinutes(ore_i[1]);   // 13:30 → 840 minute
                
                // Verificăm dacă există suprapunere

                // D: 13:00-14:30, I: 14:00-15:30
                if (startI < endD && startD < startI) {
                    System.out.println("❌ Suprapunere detectată!");
                    return false;
                }

                // D: 13:00-14:30, I: 12:00-13:30
                if (endI > startD && endI < endD) {
                    System.out.println("❌ Suprapunere detectată!");
                    return false;
                }
                //                    
                // D: 13:00-14:30, I: 13:30-14:00
                if (startI > startD && endI < endD) {
                    System.out.println("❌ Suprapunere detectată!");
                    return false;
                }

                }

                }
        return true;
        }
}