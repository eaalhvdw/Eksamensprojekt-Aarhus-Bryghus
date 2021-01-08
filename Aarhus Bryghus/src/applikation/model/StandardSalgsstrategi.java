package applikation.model;

public class StandardSalgsstrategi implements Salgsstrategi {

	private TilstandEnum tilstand;
	
	public StandardSalgsstrategi() {
		tilstand = TilstandEnum.HJEMME;
	}

	@Override
	public void koeb() {
		tilstand = TilstandEnum.SOLGT;
		
	}

	@Override
	public void saetTilstand(TilstandEnum tilstand) {
		this.tilstand = tilstand;
		
	}

	@Override
	public TilstandEnum hentTilstand() {
		return tilstand;
	}
	

}
