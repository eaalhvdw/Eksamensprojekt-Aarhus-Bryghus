package applikation.model;

public interface Salgsstrategi {
	
	public void koeb();

	public void saetTilstand(TilstandEnum tilstand);

	public TilstandEnum hentTilstand();
}
