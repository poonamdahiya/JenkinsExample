import com.applitools.eyes.*;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import junit.framework.JUnit4TestAdapter;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
public class ExampleTest {
    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    private Eyes eyes = new Eyes();
    private WebDriver driver;
//    private String applitoolsKey = System.getenv("APPLITOOLS_API_KEY");
    private static BatchInfo batch;
//    private https://hooks.slack.com/services/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
    @BeforeClass
    public static void batchInitialization(){
        batch = new BatchInfo("MyTestBatch");
        System.out.println("Batch Name :" + batch);
    }
    @Before
    public void setUp () throws Exception {
//        eyes.setAppName("Applitools Demo");
        eyes.setApiKey("JgD6gcNB7106c3oQgyOrLimZI7tId1F8R98Gb1r3D6IgTQ110");
//        System.out.println("Applitools API Key :" + System.getenv("APPLITOOLS_API_KEY"));

        eyes.setHideScrollbars(true);
        //Take a full page screenshot
        eyes.setForceFullPageScreenshot(false);
        //Stitch pages together and remove floating headers and footers...
        eyes.setStitchMode(StitchMode.CSS);
        //Set match level to Layout2 for dynamic content sites.
        eyes.setMatchLevel(MatchLevel.STRICT);

//        export APPLITOOLS_BATCH_ID=`echo ${GIT_COMMIT}`
        //Set batch name. Essentially a folder name to group your images.
        //Set only once per Jenkins job
        //http://support.applitools.com/customer/en/portal/articles/2689601-integration-with-the-jenkins-plugin
        if (System.getenv("APPLITOOLS_BATCH_ID") != null ) {
            System.out.println("Applitools Batch ID is " + System.getenv("APPLITOOLS_BATCH_ID"));
            batch.setId(System.getenv("APPLITOOLS_BATCH_ID"));
        System.out.println("APPLITOOLS_BATCH_ID:" + System.getenv("APPLITOOLS_BATCH_ID"));
        }
        //End of - Set only once per Jenkins job
        //batch.
        eyes.setBatch(batch);
        eyes.setBranchName("Release");
//        eyes.setParentBranchName("Release");
        //set new baseline images. Use this when your site has changed without having to do in the dashboard.
        //eyes.setSaveFailedTests(true);
        //some changes
        //output detailed log data to console...
        eyes.setLogHandler(new StdoutLogHandler(true));
    }

    @Test
    public void GithubHomePage () throws Exception {
        driver = new ChromeDriver();
        eyes.open(driver, "Demo App", "Jenkins GitHub Example", new RectangleSize(900, 600));
        driver.get("https://demo.applitools.com");
        // Visual checkpoint #1 - Check the login page.
        eyes.checkWindow("Home Page");
        // This will create a test with two test steps.
//        driver.findElement(By.id("log-in")).click();
//        // Visual checkpoint #2 - Check the app page.
//        eyes.checkWindow("App Window");
        // End the test.
    }
    @After
    public void tearDown () throws Exception {
        driver.quit();
        TestResults results = eyes.close(false);
        EyesSlack.post(results , "https://hooks.slack.com/services/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");
        System.out.println("Results: ");
        System.out.println(results);
        assertEquals(true, results.isPassed());
    }
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new JUnit4TestAdapter(ExampleTest.class));
    }
}