package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import Audio.AudioJUnit;
import GameLogic.GameLogicJUnit;
import GameLogic.PhysicsTest;
import Graphics.GraphicsJUnit;
import Graphics.HangerOnJUnit;
import Networking.NetworkingJUnit;
import UserInterface.UserInterfaceJUnit;
import ai.AIJUnit;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   GameLogicJUnit.class,
   NetworkingJUnit.class,
   PhysicsTest.class,
   AIJUnit.class,
   UserInterfaceJUnit.class,
   GraphicsJUnit.class,
   HangerOnJUnit.class,
   AudioJUnit.class
})
public class JunitTestSuite {   
}
