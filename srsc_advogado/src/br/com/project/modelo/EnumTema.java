package br.com.project.modelo;

public enum EnumTema {

	afterdark("Afterdark","afterdark"),
	afternoon("Afternoon","afternoon"),
	afterwork("Afterwork","afterwork"),
	aristo("Aristo","aristo"),
	blackTie("Black-Tie","black-tie"),
	blitzer("Blitzer","blitzer"),
	bluesky("Bluesky","bluesky"),
	bootstrap("Bootstrap","bootstrap"),
	casablanca("Casablanca","casablanca"),
	cruze("Cruze","Cruze"),
	darkHive("Dark-Hive","dark-hive"),
	delta("Delta","Delta"),
	dotLuv("Dot-Luv","dot-luv"),
	eggplant("Eggplant","eggplant"),
	exciteBike("Excite-Bike","excite-bike"),
	flick("Flick","flick"),
	glassX("Glass-X","glass-x"),
	home("Home","home"),
	hotSneaks("Hot-Sneaks","hot-sneaks"),
	humanity("Humanity","humanity"),
	leFrog("Le-Frog","le-frog"),
	midnight("Midnight","midnight"),
	mintChoc("Mint-Choc","mint-choc"),
	overcast("Overcast","overcast"),
	pepperGrinder("Pepper-Grinder","pepper-grinder"),
	redmond("Redmond","redmond"),
	rocket("Rocket","rocket"),
	sam("Sam","sam"),
	smoothness("Smoothness","smoothness"),
	southStreet("South-Street","south-street"),
	start("Start","start"),
	sunny("Sunny","sunny"),
	swankyPurse("Swanky-Purse","swanky-purse"),
	trontastic("Trontastic","trontastic"),
	uiDarkness("UI-Darkness","ui-darkness"),
	uiLightness("UI-Lightness","ui-lightness"),
	vader("Vader","vader");
	
	
	private String displayName;
	private String name;

	private EnumTema(String displayName, String name) {
		this.setDisplayName(displayName);
		this.setName(name);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
