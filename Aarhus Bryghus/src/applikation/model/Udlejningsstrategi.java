package applikation.model;

public class Udlejningsstrategi implements Salgsstrategi {

	private TilstandEnum tilstand;
	
	public Udlejningsstrategi() {
		tilstand = TilstandEnum.HJEMME;
	}
	
	@Override
	public void koeb() {
		if(tilstand.equals(TilstandEnum.UDLEJET) || tilstand.equals(TilstandEnum.PLANLAGT_UDLEJNING)) {
			tilstand = TilstandEnum.HJEMME;
		}
		else {
			tilstand = TilstandEnum.PLANLAGT_UDLEJNING;
		}
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
