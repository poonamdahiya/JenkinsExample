import com.applitools.eyes.*;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import junit.framework.JUnit4TestAdapter;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.SecureRandom;

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
//    private https://hooks.slack.com/services/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
    @BeforeClass
    public static void batchInitialization(){
        batch = new BatchInfo("MyTestBatch");
        System.out.println("Batch Name :" + batch);
        String sequenceName = "Demo App";
        batch.setNotifyOnCompletion(true);

//        System.setProperty("https.proxyHost", "proxy");
//        System.setProperty("https.proxyPort", "8080");

//        SSLContext ctx = SSLContext.getInstance("TLS");
//        ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
//        SSLContext.setDefault(ctx);

//        batch.setSequenceName(sequenceName);
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
        String batchId = System.getenv("APPLITOOLS_BATCH_ID");
        System.out.println("Applitools Batch ID is " + batchId);

        if (batchId != null ) {
            System.out.println("Applitools  Batch ID is " + batchId);
            batch.setId(batchId);
        }

        eyes.setBatch(batch);
//        eyes.setBranchName("MyBranch");
//        comment
//        eyes.setParentBranchName("ParentBranch");
        //set new baseline images. Use this when your site has changed without having to do in the dashboard.
        //eyes.setSaveFailedTests(true);
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
//        TestResults results = eyes.close(false);
        //test
        TestResultsSummary allTestResults = runner.getAllTestResults();
        System.out.println(allTestResults);
//        EyesSlack.post(allTestResults , "https://hooks.slack.com/services/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");
//        System.out.println("Results: ");
//        System.out.println(allTestResults);
//        assertEquals(true, allTestResults.getAllResults().equals());
    }
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new JUnit4TestAdapter(ExampleTest.class));
    }
}