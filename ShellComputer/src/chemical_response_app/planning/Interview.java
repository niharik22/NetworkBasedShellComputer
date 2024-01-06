package chemical_response_app.planning;

/**
 * Interview collects information about the incident and responder.
 * The interview is built from (Swing) data in the responder view and used by the plan factory.
 * It is also possible to create an Interview for testing purposes as in PlannerTest.
 */
class Interview {

	String lastName = "UNK";
	String firstName = "UNK";
	String building = "UNK";
	String room = "UNK";
	SpillCase materialSpilled = SpillCase.standard;
	int spillSize = 0;
	
	public String toString(){
		return new String("contact: "+firstName+" "+lastName+
				          "\n from building "+ building+" room "+room+
				          "\n reports a "+materialSpilled+" spill of "+spillSize+" inches.");
	}

	public Interview(String[] inputs, SpillCase material, int sz) {
		if (inputs.length >= 4) {
			lastName = inputs[0];
			firstName = inputs[1];
			building = inputs[2];
			room = inputs[3];
		} else {
			for (int i=0; i<5; i++){
				inputs[i] = "bad input";
			}
		}
		materialSpilled = material;
		spillSize = sz;
	}

}