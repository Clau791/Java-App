import java.util.Scanner;

public class MultipleAdd {
    public static void main(String[] args) {

        // Citire date de la tastatură
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceti cate rezerverzari vreti sa faceti: ");
        int n = scanner.nextInt();
        
        

        for(int i = 0 ; i < n ; i++){
            System.out.print("Introduceti ID-ul salii pe care o doriti sa o rezervati: ");
            int id_sala = scanner.nextInt();
            scanner.nextLine(); // Consumă newline-ul rămas

            System.out.print("Introdu numele: ");
            String utilizator = scanner.nextLine();
            scanner.nextLine(); // Consumă newline-ul rămas

            System.out.print("Introdu ziua: ");
            int zi = scanner.nextInt();
            scanner.nextLine(); // Consumă newline-ul rămas

            System.out.print("Introdu intervalul: ");

            String interval = scanner.nextLine();

            RezervareService r = RezervareService.getInstance();
            r.adaugaRezervare(id_sala, utilizator, zi, interval);
        }
        scanner.close();

    }
}
