import com.applitools.eyes.*;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import junit.framework.JUnit4TestAdapter;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class ExampleTest {
    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    ClassicRunner runner = new ClassicRunner();
    private Eyes eyes = new Eyes(runner);
    private WebDriver driver;

    private static BatchInfo batch;

    @BeforeClass
    public static void batchInitialization(){
        batch = new BatchInfo("MyTestBatch");
        System.out.println("Batch Name :" + batch);
        String sequenceName = "Demo App";
    }

    @Before
    public void setUp () throws Exception {
//        eyes.setAppName("Applitools Demo");
        eyes.setApiKey("0zWGBGZNbDTfD9jmmvHMRfX9kltnHlw4BcvQHEAZj7I110");
        eyes.setHideScrollbars(true);
        //Take a full page screenshot
        eyes.setForceFullPageScreenshot(true);
        //Stitch pages together and remove floating headers and footers...
        eyes.setStitchMode(StitchMode.CSS);
        //Set match level to Layout2 for dynamic content sites.
        eyes.setMatchLevel(MatchLevel.STRICT);
        String batchId = System.getenv("APPLITOOLS_BATCH_ID");
        System.out.println("Applitools Batch ID is " + batchId);

        if (batchId != null ) {
            System.out.println("Applitools  Batch ID is " + batchId);
            batch.setId(batchId);
        }

        eyes.setBatch(batch);
        eyes.setLogHandler(new StdoutLogHandler(true));
    }

    @Test
    public void GithubHomePage () throws Exception {
        driver = new ChromeDriver();
        eyes.open(driver, "Demo App", "Jenkins GitHub Example", new RectangleSize(900, 600));
        driver.get("https://demo.applitools.com");
        // Visual checkpoint #1 - Check the login page.
        eyes.checkWindow("Home Page");
//         This will create a test with two test steps.
        driver.findElement(By.id("log-in")).click();
        // Visual checkpoint #2 - Check the app page.
        eyes.checkWindow("App Window");

    }
    @After
    public void tearDown () throws Exception {
        driver.quit();
        eyes.close(false);

        TestResultsSummary allTestResults = runner.getAllTestResults();
        System.out.println(allTestResults);

    }
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new JUnit4TestAdapter(ExampleTest.class));
    }
}