package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import de.db.derPate.model.Godfather;

class UserTest {
	

	  @Before
	  public void initCalculator() {
		  
	  }

	@Test
	void userFirstNameTest() {
		assertEquals(new Godfather(null, null).getFirstName(), null);
	}

}
