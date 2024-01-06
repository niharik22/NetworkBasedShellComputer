package chemical_response_app.planning;

/**
 * Enumeration of possible response states.
 * Suggested by information in documentation.
 * Not yet used effectively at this stage.
 *
 */
public enum ResponseState {

	immediate, contain, clean, notify;
	
	public ResponseState next (){
		switch (this){
		case immediate: return contain;
		case contain: return clean;
		case clean: return notify;
		default: return notify;
		}
	}

}
