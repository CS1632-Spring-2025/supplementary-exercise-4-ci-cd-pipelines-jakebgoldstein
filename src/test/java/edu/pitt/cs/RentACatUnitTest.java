package edu.pitt.cs;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatUnitTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	ByteArrayOutputStream out; // Output stream for testing system output
	PrintStream stdout; // Print stream to hold the original stdout stream
	String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n") for use in assertEquals

	@Before
	public void setUp() throws Exception 
	{

    r = RentACat.createInstance(InstanceType.BUGGY); 


    c1 = Mockito.mock(Cat.class);
    c2 = Mockito.mock(Cat.class);
    c3 = Mockito.mock(Cat.class);

    // Stubbing the methods of the mock Cat objects
    Mockito.when(c1.getId()).thenReturn(1);
    Mockito.when(c1.getName()).thenReturn("Jennyanydots");
    Mockito.when(c1.toString()).thenReturn("ID 1. Jennyanydots");

    Mockito.when(c2.getId()).thenReturn(2);
    Mockito.when(c2.getName()).thenReturn("Old Deuteronomy");
    Mockito.when(c2.toString()).thenReturn("ID 2. Old Deuteronomy");

    Mockito.when(c3.getId()).thenReturn(3);
    Mockito.when(c3.getName()).thenReturn("Mistoffelees");
    Mockito.when(c3.toString()).thenReturn("ID 3. Mistoffelees");

    // 3. Redirect system output
    stdout = System.out;  // Backup the original System.out
    out = new ByteArrayOutputStream();
	}
	
	

	@After
	public void tearDown() throws Exception {
		// Restore System.out to the original stdout
		System.setOut(stdout);

		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix 
	 * hapter on using reflection on how to do this.  Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNullNumCats0() throws Exception
	{
        //RentACat r = new RentACatImpl();  

        // Execution steps: Call getCat(2)
        Method getCatMethod = r.getClass().getDeclaredMethod("getCat", int.class);
        getCatMethod.setAccessible(true);
        Cat result = (Cat) getCatMethod.invoke(r, 2);

        assertNull(result);
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix 
	 * hapter on using reflection on how to do this.  Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNumCats3() 
	{
		try {
          
            //RentACat r = new RentACatImpl();  
            Cat c1 = new CatImpl(1, "Cat1"); 
            Cat c2 = new CatImpl(2, "Cat2");
            Cat c3 = new CatImpl(3, "Cat3");

            r.addCat(c1);
            r.addCat(c2);
            r.addCat(c3);

            // Execution steps: Call getCat(2)
            Method getCatMethod = r.getClass().getDeclaredMethod("getCat", int.class);
            getCatMethod.setAccessible(true);
            Cat result = (Cat) getCatMethod.invoke(r, 2);


            assertNotNull(result);
            assertEquals(2, result.getId());

        } 
		catch (Exception e) 
		{

            fail("Reflection failed: " + e.getMessage());
        }
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats0() 
	{
        //RentACat r = new RentACatImpl();  

        // Execution steps: Call listCats()
        String result = r.listCats();
        assertEquals("", result);
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats3() 
	{
		try {
            //RentACat r = new RentACatImpl();  
            Cat c1 = new CatImpl(1, "Jennyanydots");  
            Cat c2 = new CatImpl(2, "Old Deuteronomy");
            Cat c3 = new CatImpl(3, "Mistoffelees");

            r.addCat(c1);
            r.addCat(c2);
            r.addCat(c3);

            // Execution steps: Call listCats()
            String result = r.listCats();

            String expected = "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n";
            assertEquals(expected, result);

        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is false.
	 *                 c2 is not renamed to "Garfield".
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRenameFailureNumCats0() 
	{
            // Preconditions: r has no cats
            //RentACat r = new RentACatImpl(); 
            boolean result = r.renameCat(2, "Garfield");

            assertFalse(result);
			verify(c1, times(0)).renameCat(anyString());
    		verify(c2, times(0)).renameCat(anyString());
    		verify(c3, times(0)).renameCat(anyString());
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is true.
	 *                 c2 is renamed to "Garfield".
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRenameNumCat3() 
	{
		try {

			//RentACat r = Mockito.mock(RentACat.class);
            Cat c1 = new CatImpl(1, "Jennyanydots"); 
            Cat c2 = new CatImpl(2, "Old Deuteronomy");
            Cat c3 = new CatImpl(3, "Mistoffelees");

            r.addCat(c1);
            r.addCat(c2);
            r.addCat(c3);

			when(r.renameCat(eq(2), eq("Garfield"))).thenReturn(true);

            boolean result = r.renameCat(2, "Garfield");

            assertTrue(result);
            verify(r, times(1)).renameCat(eq(2), eq("Garfield"));

        } 
		catch (Exception e)
		{
        	fail("Test failed: " + e.getMessage());
        }
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is rented as a result of the execution steps.
	 *                 System output is "Old Deuteronomy has been rented." + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRentCatNumCats3() 
	{
		try {
			//RentACat r = Mockito.mock(RentACat.class);

            Cat c1 = new CatImpl(1, "Jennyanydots"); 
            Cat c2 = new CatImpl(2, "Old Deuteronomy");
            Cat c3 = new CatImpl(3, "Mistoffelees");

			r.addCat(c1);
			r.addCat(c2);
			r.addCat(c3);
			
			when(r.rentCat(eq(2))).thenReturn(true);

			boolean result = r.rentCat(2);

			assertTrue(result);
			verify(r, times(1)).rentCat(eq(2));
		}
		catch(Exception e)
		{
			fail("Test failed: " + e.getMessage());
		}
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 is not rented as a result of the execution steps.
	 *                 System output is "Sorry, Old Deuteronomy is not here!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRentCatFailureNumCats3() 
	{
		try
		{
		
		//RentACat r = Mockito.mock(RentACat.class);
		Cat c1 = new CatImpl(1, "Jennyanydots");  
		Cat c2 = new CatImpl(2, "Old Deuteronomy");
		Cat c3 = new CatImpl(3, "Mistoffelees");
		
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		Mockito.when(r.rentCat(eq(2))).thenReturn(true);

		boolean result = r.rentCat(2);

		assertTrue(result);
		verify(r, times(1)).rentCat(eq(2));

		}
		catch(Exception e)
		{
			fail("Test failed: " + e.getMessage());
		}
	}	

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is returned as a result of the execution steps.
	 *                 System output is "Welcome back, Old Deuteronomy!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testReturnCatNumCats3() 
	{
			//RentACat r = Mockito.mock(RentACat.class);
	
			Cat c1 = new CatImpl(1, "Jennyanydots");
			Cat c2 = new CatImpl(2, "Old Deuteronomy");
			Cat c3 = new CatImpl(3, "Mistoffelees");
	
			r.addCat(c1);
			r.addCat(c2);
			r.addCat(c3);
	
			Mockito.when(r.rentCat(eq(2))).thenReturn(true);
			r.rentCat(2);
	
			Mockito.when(r.returnCat(eq(2))).thenReturn(true);
	
			boolean result = r.returnCat(2);
			verify(r, times(1)).returnCat(eq(2));
	
			assertTrue(result); 
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 is not returned as a result of the execution steps.
	 *                 System output is "Old Deuteronomy is already here!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testReturnFailureCatNumCats3() 
	{
		try {
			//RentACat r = Mockito.mock(RentACat.class);
			Cat c1 = new CatImpl(1, "Jennyanydots");
			Cat c2 = new CatImpl(2, "Old Deuteronomy");
			Cat c3 = new CatImpl(3, "Mistoffelees");
	
			r.addCat(c1);
			r.addCat(c2);
			r.addCat(c3);
	
			Mockito.when(r.returnCat(eq(2))).thenReturn(false);
	
			boolean result = r.returnCat(2);
	
			assertFalse(result);
			verify(r, times(1)).returnCat(eq(2));
	
		} 
		catch (Exception e) 
		{
			fail("Test failed: " + e.getMessage());
		}
	}

}