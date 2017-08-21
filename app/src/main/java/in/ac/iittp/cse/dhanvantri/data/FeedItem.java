package in.ac.iittp.cse.dhanvantri.data;

public class FeedItem {
	private int id;
	private String name, symptoms, prescriptions;

	public FeedItem() {
	}

	public FeedItem(int id, String name, String symptoms, String prescriptions) {
		super();
		this.id = id;
		this.name = name;
		this.symptoms = symptoms;
		this.prescriptions = prescriptions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public String getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(String prescriptions) {
		this.prescriptions = prescriptions;
	}

}
