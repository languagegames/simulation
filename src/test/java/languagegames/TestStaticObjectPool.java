package languagegames;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import conceptualspace.PerceptualObject;
import conceptualspace.Point;
import conceptualspace.SimpleObject;
import conceptualspace.StaticObjectPool;

public class TestStaticObjectPool {

	@Test
	public void createsObjectsAsSpecified() {
		final PerceptualObject object0 = new SimpleObject(new Point(0.42));
		final PerceptualObject object1 = new SimpleObject(new Point(0.43));
		final StaticObjectPool objectPool = new StaticObjectPool(asList(object0, object1));

		assertThat(objectPool.pick(), equalTo(object0));
		assertThat(objectPool.pick(), equalTo(object1));
	}

}
