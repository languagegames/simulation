package agent;

import conceptualspace.Material;


public class OracleAgent implements Agent {

	private final LabelMapping labelMapping;
	private final double weight;

	public OracleAgent(final LabelMapping labelMapping, final double weight) {
		this.labelMapping = labelMapping;
		this.weight = weight;
	}

	@Override
	public Assertion classify(final Material material) {
		return new Assertion(material, labelMapping.label(material), weight);
	}

	@Override
	public Agent learn(final Assertion label) {
		// TODO Auto-generated method stub
		return null;
	}

}
