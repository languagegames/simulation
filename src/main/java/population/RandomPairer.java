package population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPairer implements AgentPairer {

	@Override
	public List<Integer> pairingOrder(final int numAgents) {
		final List<Integer> pairingOrder = orderedList(numAgents);
		Collections.shuffle(pairingOrder);
		return pairingOrder;
	}

	private List<Integer> orderedList(final int numAgents) {
		final List<Integer> result = new ArrayList<>();
		for (int i=0; i<numAgents; i++) {
			result.add(i);
		}
		return result;
	}

}
