package model;

public enum Specialite {

	GEEK("Geek"), 
	MECANO("Mecano"), 
	ELEC("Elec"), 
	GRIBOUILLEUR("Gribouilleur"),
	MANAGEMENT("Management"),
	MATHEUX("Matheux"),
	LANGUES("Langues"),
	CULTUREGENERALE("CultureGenerale"),
	PHYSIQUECHIMIE("PhysiqueChimie"),
	INTELLO("Intello"),
	AUCUNE("Aucune");
	
	private String code;

	 private Specialite(String c) {
	   code = c;
	 }

	 public String getCode() {
	   return code;
	 }
}
