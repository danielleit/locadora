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
        return "ID: " + this.getId() + " | Título: " + this.getTitle() + " | Preço por Episódio (diário): R$" + this.getPrice() + " | Duração Total: " + this.getDuration() + " mins | Ano de Lançamento: " + this.getReleaseYear() + " | Temporadas: " + numberOfSeasons + " | Episódios: " + numberOfEpisodes;
    }
}