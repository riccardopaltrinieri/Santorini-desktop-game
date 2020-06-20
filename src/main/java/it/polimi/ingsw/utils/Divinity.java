package it.polimi.ingsw.utils;

public enum Divinity {
    Apollo,
    Artemis,
    Athena,
    Atlas,
    Demeter,
    Hephaestus,
    Minotaur,
    Pan,
    Prometheus,
    Default;

    public static String getDescrption(String divinity){
        return switch (divinity) {
            case "apollo" -> " Apollo è il Dio della musica.\n Durante il movimento, il tuo lavoratore si può muovere seguendo le solite\n" +
                    "regole, con in più la possibilità di muoversi\n nello spazio occupato da un lavoratore avversario, cosa che\n" +
                    "normalmente non sarebbe possibile.\n In tal caso si sposta il lavoratore avversario nello spazio appena lasciato libero\n. Non è possibile cambiarsi di posto con un lavoratore non raggiungibile\n da un movimento normale. ";
            default -> null;
        };
    }
}
