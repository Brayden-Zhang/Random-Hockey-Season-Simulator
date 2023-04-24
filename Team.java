import java.util.*;
public class Team{
  String teamname; 
  int powerplay, penaltykill, coaching, starpower, wins, losses, ranking, goalsfor, goalsagainst; 
  int goaldifference; 

  // following functions create random values for each stat such as powerplay
  static int randnum(int max){
   Random rand = new Random();
   return rand.nextInt(max);
  }

  public int getPowerplay(){
    powerplay = randnum(10); 
    return powerplay; 
    
  }  
  public int getPenaltykill(){
    penaltykill = randnum(10); 
    return penaltykill; 
    
    
  }
  public int getStarpower(){
    starpower = randnum(5); 
    return starpower; 
  
  }
  public int getCoaching(){
    coaching  = randnum(5);
      return coaching; 
  }
  public Team(String fullteamname){
    // constructor initializes objects
    teamname = fullteamname; 
    powerplay = getPowerplay(); 
    penaltykill = getPenaltykill(); 
    starpower = getStarpower(); 
    coaching = getCoaching(); 
    System.out.println(teamname); 
    wins = 0;
    goalsfor = 0; 
    goalsagainst = 0; 
    goaldifference = goalsfor - goalsagainst; 
    losses = 0; 
    
  }
  // I need to use a toString method to properly display the standings; otherwise, I print out the reference values not the team statistics
  public String toString(){
    return   " - " + teamname + " - " + wins + " - " + losses + " - " + goalsfor + " - " + goalsagainst;  
  }

 
}