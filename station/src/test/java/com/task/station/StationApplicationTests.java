package com.task.station;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StationApplication.class)
public class StationApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void applicationStartsTest() {
        StationApplication.main(new String[] {});
	}

}
