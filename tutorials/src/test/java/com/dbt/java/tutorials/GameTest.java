package com.dbt.java.tutorials;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

  @Mock Player player;

  @Spy List<String> enemies = new ArrayList<>();

  @InjectMocks Game game;

  @Test 
  public void attackWithSwordTest() throws Exception {
    when(player.getWeapon()).thenReturn("Sword");

    //enemies.add("Dragon");
    //enemies.add("Orc");

    //Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
    //when(enemies.get(0)).thenReturn("Dragon");

    //You have to use doReturn() for stubbing
    doReturn(2).when(enemies).size();
    
    assertThat(game.numberOfEnemies()).isEqualTo(2);
    
    verify(enemies, times(1)).size();

    assertThat(game.attack()).isEqualTo("Player attack with: Sword");
  }
 
  static class Game {
		
		  private Player player;
		
		  private List<String> opponents;
		
		  public Game(Player player, List<String> opponents) {
		    this.player = player;
		    this.opponents = opponents;
		  }
		  
		  public String attack() {
			 return "Player attack with: " + player.getWeapon();
		  }
		
		  public int numberOfEnemies() {
		    return opponents.size();
		  }
	 }
	  
	 static class Player {
	
	    private String weapon;
	
	    public Player(String weapon) {
	        this.weapon = weapon;
	    }
	
	    String getWeapon() {
	        return weapon;
	    }
	}
}