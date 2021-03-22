package br.com.project.modelo;

public enum EnumEstado {

	AC("Acre",-9.972463,-67.812749),
	AL("Alagoas",-9.667137,-35.737958),
	AP("Amapá",0.039045,-51.050099),
	AM("Amazonas",-3.133842,-60.020165),
	BA("Bahia",-12.970382,-38.512382),
	CE("Ceará",-3.730536,-38.521777),
	DF("Distrito Federal",-15.79983,-47.863711),
	ES("Espirito Santo",-20.319933,-40.336296),
	GO("Goiáis",-16.67992,-49.255032),
	MA("Maranhão",-2.532066,-44.299996),
	MT("Mato Grosso",-15.598917,-56.094894),
	MS("Mato Grosso do Sul",-20.461719,-54.612237),
	MG("Minas Gerais",-19.918339,-43.940102),
	PA("Pará",-1.452005,-48.503072),
	PB("Paraíba",-7.120034,-34.876211),
	PR("Paraná",-25.433171,-49.27147),
	PE("Pernambuco",-8.054278,-34.881256),
	PI("Piauí",-5.092628,-42.810155),
	RJ("Rio de Janeiro",-22.908892,-43.177138),
	RN("Rio Grande do Norte",-5.786403,-35.207978),
	RS("Rio Grande do Sul",-30.033914,-51.229154),
	RO("Rondánia",-8.764597,-63.903943),
	RR("Roraima",2.821734,-60.672061),
	SC("Santa Catarina",-27.593237,-48.543736),
	SP("São Paulo",-23.550483,-46.633106),
	SE("Sergipe",-10.912647,-37.053451),
	TO("Tocantins",-10.184567,-48.333654);

	private String label;
	private Double latitude;
	private Double longitude;

	private EnumEstado(String label, Double latitude, Double longitude) {
		this.label = label;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
