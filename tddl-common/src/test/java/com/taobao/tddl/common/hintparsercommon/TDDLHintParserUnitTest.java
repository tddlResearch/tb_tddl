/*(C) 2007-2012 Alibaba Group Holding Limited.	

import java.util.Map;
public class TDDLHintParserUnitTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void parseHint() throws Exception{
		String sqlHint = "/*+: a */";
		Map<String, String> hintMap = null;
		try {
			hintMap = TDDLHintParser.parseHint(sqlHint);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("�����������󣬼�ֵ�Բ�Ϊ2;: a ", e.getMessage());
		}
		sqlHint = "/*+ hint: a */";
		hintMap = TDDLHintParser.parseHint(sqlHint);
		Assert.assertEquals(1, hintMap.size());
		Assert.assertEquals("a", hintMap.get("hint"));
		
		sqlHint = "/*+ hint: a ; sb : {[(> < = != between and or 1 2 3 4 5)]} */";
		hintMap = TDDLHintParser.parseHint(sqlHint);
		Assert.assertEquals(2, hintMap.size());
		
		Assert.assertEquals("{[(> < = != between and or 1 2 3 4 5)]}", hintMap.get("sb"));

		sqlHint = " /t /*+ hint: a ; sb : {[(> < = != between and or 1 2 3 4 5)]} */ /t";
		hintMap = TDDLHintParser.parseHint(sqlHint);
		Assert.assertEquals(2, hintMap.size());
		
		Assert.assertEquals("{[(> < = != between and or 1 2 3 4 5)]}", hintMap.get("sb"));
	}
	
	@Test
	public void testParseHintStringUtil() {
		String sqlHint = "/*+ hint: a */";
		sqlHint = TDDLHintParser.substringBetween(sqlHint, "/*+","*/");
		Assert.assertEquals(" hint: a ", sqlHint);
		
		sqlHint = " /*+ hint: a */";
		sqlHint = TDDLHintParser.substringBetween(sqlHint, "/*+","*/");
		Assert.assertEquals(" hint: a ", sqlHint);
		
		
		sqlHint = " /*+ hint: a */ ";
		sqlHint = TDDLHintParser.substringBetween(sqlHint, "/*+","*/");
		Assert.assertEquals(" hint: a ", sqlHint);
		
		sqlHint = "/* + hint: a */";
		try {
			sqlHint = TDDLHintParser.substringBetween(sqlHint, "/*+","*/");
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("can't find start :/*+", e.getMessage());
		}
		
		
		sqlHint = "/*+ hint: a * /";
		try {
			sqlHint = TDDLHintParser.substringBetween(sqlHint, "/*+","*/");
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("can't find end :*/", e.getMessage());
		}
	
		
		sqlHint = "/*+ hint: a ";
		try {
			sqlHint = TDDLHintParser.substringBetween(sqlHint, "/*+","*/");
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("can't find end :*/", e.getMessage());
		}
	}

}