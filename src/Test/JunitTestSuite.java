package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import GameLogic.GameLogicJUnit;
import GameLogic.PhysicsTest;
import Graphics.GraphicsJUnit;
import Graphics.HangerOnJUnit;
import Networking.NetworkingJUnit;
import UserInterface.PlayersTest;
import UserInterface.mainMenuNetworkTest;
import UserInterface.mainMenuTest;
import ai.AIJUnit;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   GameLogicJUnit.class,
   NetworkingJUnit.class,
   PhysicsTest.class,
   AIJUnit.class,
   mainMenuNetworkTest.class,
   mainMenuTest.class,
   PlayersTest.class,
   GraphicsJUnit.class,
   HangerOnJUnit.class
})
public class JunitTestSuite {   
}
