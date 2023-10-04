package models;

public class TVSeries extends MediaItem {
    private int numberOfEpisodes;
    private int numberOfSeasons;

    public TVSeries(int id, String title, double price, int duration, int releaseYear, int numberOfEpisodes, int numberOFSeasons) {
        super(id, title, price, duration, releaseYear);
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOFSeasons;
    }

    @Override
    public String toString(){
        return "ID: " + this.getId() + " | Titulo: " + this.getTitle() + " | Preco por Episodio (diario): R$" + this.getPrice() + " | Duracao Total: " + this.getDuration() + " mins | Ano de Lancamento: " + this.getReleaseYear() + " | Temporadas: " + numberOfSeasons + " | Episodios: " + numberOfEpisodes;
    }
}