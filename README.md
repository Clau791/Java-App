# Sistem de Rezervare a Salilor 

Aceasta este o aplicatie Java simpla care demonstreaza utilizarea principiilor de programare orientata pe obiecte.
Aplicatia gestioneaza o lista de sali de curs si permite utilizatorilor sa creeze conturi, sa vizualizeze salile disponibile, sa faca rezervari si sa le modifice.

## Structura Proiectului


```
Proiect_Etapa1
├── src
│   ├── lib
│   │   └──  postgresql-42.7.5.jar
│   ├── Main.java
│   ├── models
│   │   └── Sala.java
│   │   └── Sala_Amfiteatru.java
│   │   └── Sala_Seminar.java
│   │   └── Sala_Laborator.java
│   │   └── Facultate.java
│   │   └── Rezervare.java
│   │   └── Utilizator.java
│   │   └── Profesor.java
│   │   └── Student.java
│   └── services
│       └── AuditService.java
│       └── DatabaseConnection.java
│       └── RezervareService.java
│       └── Service.java
├── .gitignore
└── README.md
```

## Descrierea Sistemului

**Sistemul de Rezervare a Salilor** permite utilizatorilor sa efectueze diverse actiuni legate de gestionarea rezervarilor pentru salile de curs. Acest sistem este destinat studentilor si profesorilor, avand functionalitati dedicate fiecarei categorii de utilizatori.

### Functionalitati

1. **Creaza cont** - Permite utilizatorilor sa creeze un cont ca `Student` sau `Profesor`.
2. **Logare** - Permite utilizatorilor sa se logheze intr-un cont existent in baza de date.
3. **Vizualizeaza salile disponibile** - Permite vizualizarea salilor disponibile pentru rezervare.
4. **Vizualizeaza salile disponibile dintr-o anumita facultate** - Permite vizualizarea salilor disponibile dintr-o facultate aleasa.
5. **Rezerva o sala de seminar** - Permite utilizatorilor sa rezerveze o sala de seminar.
6. **Rezerva o sala de laborator** - Permite profesorilor sa rezerveze o sala de laborator.
7. **Rezerva o sala de curs** - Permite profesorilor sa rezerveze o sala de curs.
8. **Rezerva mai multe sali** - Permite studentilor sa rezerveze pana la 3 sali simultan.9
9. **Vezi rezervarile tale** - Permite utilizatorilor sa vizualizeze rezervarile lor.
10. **Anuleaza o rezervare** - Permite utilizatorilor sa anuleze sau sa modifice rezervarile existente (pana la 10 modificari).

## Clase si Componente

### 1. `Sala`
Clasa de baza pentru toate tipurile de sali. Aceasta este mostenita de clasele `Sala_Amfiteatru`, `Sala_Seminar` si `Sala_Laborator`.

### 2. `Facultate`
Clasa care contine toate salile dintr-o facultate. Permite gestionarea salilor si interactiunea cu acestea.

### 3. `Rezervare`
Folosita pentru a pastra evidenta tuturor rezervarilor si pentru a le valida. Include detalii despre salile rezervate, ziua si intervalul orar.

### 4. `Utilizator`
Clasa de baza pentru utilizatorii sistemului. Aceasta poate fi un `Student` sau un `Profesor`, iar acestia au privilegii diferite in functie de tipul lor.

### 5. `Student` si `Profesor`
Aceste clase sunt derivate din clasa `Utilizator`. `Profesorul` are mai multe privilegii de rezervare, cum ar fi rezervarile recurente, in timp ce `Studentul` are mai multe limitari, cum ar fi numarul maxim de sali ce pot fi rezervate simultan.

### 6. `Service`
Clasa serviciu care expune operatiunile sistemului, inclusiv crearea conturilor, vizualizarea salilor, efectuarea rezervarilor si gestionarea acestora.

### 7. `Main`
Clasa principala care contine meniul interactiv si face apeluri catre clasa `Service` pentru a indeplini cerintele utilizatorului.



## Implementare

- **Accesul la date**: Fiecare clasa are metode de acces pentru a manipula atributele private. Acestea sunt utilizate pentru a gestiona informatiile despre utilizatori, sali si rezervari.
  
- **Structuri de date**:
  - Am folosit functie de sortare in Clasa Sala pentru a afisa rezervarile ei in mod ordonat
  - Am folosit Vector in clasele Sala, Facultate si Utilizatori pentru a pastra datele ordonat.

- **Mostenire**:
  - Am utilizat mostenirea pentru a crea tipuri specifice de sali (Sala_Amfiteatru, Sala_Seminar, Sala_Laborator) si pentru a diferentia intre utilizatori (Student, Profesor).
  
- **Clasa Serviciu**:
  - Clasa Service expune toate operatiunile necesare pentru gestionarea conturilor, rezervarilor si vizualizarea salilor.

- **Clasa Principala**:
  - Clasa Main contine doar meniul principal care interactioneaza cu utilizatorul si face apeluri la clasa Service pentru a indeplini cerintele.

## Partea 2:
- Am folosit **Postgre SQL 17** pentru a stoca date in DB.
- Pentru functionare corecta trebuie rulat fisierul **SQL_BD** pentru a avea obiecte de tip : sala, facultate pentru a face rezervari. 
- Programul primeste operatia ceruta de utilizator -> service -> RezervareService(se preiau obiectele din DB) -> returneaza operatia ceruta(scrie in DB daca este cazul) -> raporteaza in audit.csv 
- Pentru realizarea cerintelor:
  - Am folosit operatii de create, read, update si delete in RezervareService si am facut clasa RezervareService de tip singleton
  - Serviciul de Audit se realizeaza prin clasa AuditService care scrie/creaza(daca nu exista) in audit.csv orice operatie facut de utilizator