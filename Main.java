

import java.util.*; 

class Main {
  public static void main(String[] args) {
    System.out.println("Welcome to Brayden's HOCKEY Season Simulator! \n Here are the 8 teams. ");  

// getting team names

  String[] city = {"Toronto", "Montreal", "Vancouver", "New York", "Calgary", "Miami", " Los Angeles", "Boston" }; 
  List cityList = new ArrayList<>(Arrays.asList(city)); 
  String[] animals = {"Beavers", "Hogs", "Lions", "Kraken", "Bears" ,"Penguins", "Moose", "Eagles"}; 
  List animalsList = new ArrayList<>(Arrays.asList(animals)); 

  ArrayList<String> teamnames = new ArrayList<String>(); 

  
    
 
  // creating team names by randomly selecting from city and animal arraylists

  for (int i = 0; i< 8; i++){
    int a = randnum(cityList.size()); 
    int b = randnum(animalsList.size()); 
    String fullteamname = (cityList.get(a) + " " + animalsList.get(b)); 
    teamnames.add(fullteamname); 
    
    // in order to prevent repeated names I remove names that have already been used
    
    cityList.remove(a); 
    animalsList.remove(b);

    
  }
    
// creating 8 team objects to be stored in an array
    Team a1 = new Team(teamnames.get(0)); 
    Team a2 = new Team(teamnames.get(1)); 
    Team a3 = new Team(teamnames.get(2)); 
    Team a4 = new Team(teamnames.get(3)); 
    Team a5 = new Team(teamnames.get(4)); 
    Team a6 = new Team(teamnames.get(5)); 
    Team a7 = new Team(teamnames.get(6)); 
    Team a8 = new Team(teamnames.get(7)); 
    Team[] teams = {a1,a2,a3,a4,a5,a6,a7,a8}; 

    // calling the round robin and playoff simulator functions 
    round_robin(teams); 
    playoffs_output(teams); 

    }

  
  static int randnum(int max){
   Random rand = new Random();
   return rand.nextInt(max);
  }

   // create and output matchups between teams and output standings
  // implementation of the circle method from https://en.wikipedia.org/wiki/Round-robin_tournament#Scheduling_algorithm
  public static void round_robin(Team[] teams){
    for (int i = 0; i < 14; i++){
      System.out.println("\n Week " + (i+1)); 
      Team swapping = teams[7]; 
       // keeping one team in place while rotating the others by one spot lower
      for (int j = 7; j>1; j--){
        teams[j] = teams[j-1]; 
        }
      teams[1] = swapping; 
      
      for (int k = 0; k < 4; k++ ){
        // matching the two teams for a game; teams that are halfway apart from each other play together
        Team team1 = teams[k]; 
        Team team2 = teams[k+4]; 
        int team1score = TeamScore(team1); 
        int team2score = TeamScore(team2); 
         
        
// print game results in correct format
        System.out.println(team1.teamname + " " + team1score + ", " + team2.teamname + " " + team2score);
        // update team stats such as wins, losses, goals for, goals against
        gameUpdate(team1, team2, team1score, team2score); 

        
      }
      System.out.println("Week " + (i+1) + " Standings");
      System.out.println("Place - team - wins - losses - GF - GA");
      // sorting teams by standings with consideration of goal differential
     mergeSort(teams); 
     standingsbygoals(teams); 
      // print standings
     for (int r=0; r<teams.length; r++){
       System.out.print((r+1)); //print ranking

        System.out.println(teams[r]);
      }
      
      
      
    }

    
  
    
    
   
    }

// determining scores of a team based on their stats
  public static int TeamScore(Team team1){   
    int teamgoals = Math.round(((team1.powerplay + (randnum(10)))/10 *  (team1.penaltykill + randnum(10))/10 + (team1.coaching/2 + randnum(3))));
    return teamgoals; 
   }

  // method that updates team stats after every game
    public static void gameUpdate(Team team1, Team team2, int team1score, int team2score){
  
      team1.goalsfor +=team1score; 
      team1.goalsagainst +=team2score; 
      team2.goalsfor +=team2score; 
      team2.goalsagainst +=team1score; 
      
      
  
      if (team1score > team2score){
        // team1 wins then add to team1 record
        team1.wins +=1; 
        team2.losses +=1; 
        
      }
      else if (team1score < team2score){
        team1.losses +=1;
        team2.wins +=1; 
      }
        // if tie, then star power determines who wins; counts as a win in shootout
      else{
        if (team1.starpower > team2.starpower){
          team1.wins +=1; 
          team1.goalsfor +=1; 
          team2.goalsagainst +=1; 
          team2.losses +=1; 
          
          team1score +=1; 
          System.out.println(team1.teamname + " wins in a shootout");
        }
        else if (team2.starpower > team1.starpower){
          team1.losses +=1;
          team2.goalsfor +=1; 
          team1.goalsagainst +=1; 
          team2.wins +=1;
          team2score +=1; 
          System.out.println(team2.teamname + " wins in a shootout");
        }
        // if still tied, lucky team prevails
        else{
          int doubleovertimeluck = randnum(1); 
          if (doubleovertimeluck ==1){
            team1.wins +=1; 
            team2.losses +=1; 
            team1score +=1; 
          
          }
          else{ 
            team1.losses +=1;
            team2.wins +=1; }
            team2score +=1; 

        }
        
      }
     
    }


  // simulates playoffs by playing games for all three rounds and eliminating losing teams
  public static void playoffs_output(Team[] teams){
    // new arraylist that has all the team objects 
    ArrayList<Team> winningteams = new ArrayList<Team>(); 
    for (Team x : teams){
      winningteams.add(x); 
    }
    for (int i =0; i<4; i++){
      System.out.println("\n"); 

      System.out.println("First Round of Playoffs: "); 
  
// matching up 1st against 8th, 2nd and 7th, etc
      Team team1 = teams[i]; 
      Team team2 = teams[7-i]; 
      int team1score = TeamScore(team1); 
      int team2score = TeamScore(team2); 
      
        // outputting and updating game results
        System.out.println(team1.teamname + " " + team1score + ", " + team2.teamname + " " + team2score);
      gameUpdate(team1, team2, team1score, team2score); 
      
// losing teams are removed from arraylist
      if (team1score > team2score){
        winningteams.remove(team2);  
        System.out.println(team1.teamname + " advances!");
      }
        
      else if (team1score <team2score){
        winningteams.remove(team1); 
        System.out.println(team2.teamname + " advances!");

      }
      else{
        if (team1.starpower > team2.starpower){
          winningteams.remove(team2); 
                  System.out.println(team1.teamname + " advances!");

        }
        else{
          winningteams.remove(team1); 
                  System.out.println(team2.teamname + " advances!");

        }
      }
    }

    for (int i =0; i<2; i++){
      System.out.println("\n"); 
      System.out.println("Second Round of Playoffs");
      // winning teams from round 1 now play against each other; matchups are organized and played in a similar manner. 
      Team team1 = winningteams.get(i); 
      Team team2 = winningteams.get(3-(i));
      int team1score = TeamScore(team1); 
      int team2score = TeamScore(team2); 
      System.out.println(team1.teamname + " " + team1score + ", " + team2.teamname + " " + team2score);
              gameUpdate(team1, team2, team1score, team2score); 

      if (team1score > team2score){
        winningteams.remove(team2);  
                System.out.println(team1.teamname + " advances!");

      }
        
      else if (team1score <team2score){
        winningteams.remove(team1); 
                System.out.println(team2.teamname + " advances!");

      }
      else{
        if (team1.starpower > team2.starpower){
          winningteams.remove(team2); 
                  System.out.println(team1.teamname + " advances!");

        }
        else{
          winningteams.remove(team1); 
                  System.out.println(team2.teamname + " advances!");

        } 
        
      }
      
    }
  
    System.out.println("\n 3rd and Final Round of Playoffs"); //only one game; formatting is similar
    Team team1 = winningteams.get(0); 
    Team team2 = winningteams.get(1);
    int team1score = TeamScore(team1); 
    int team2score = TeamScore(team2); 
    System.out.println(team1.teamname + " " + team1score + ", " + team2.teamname + " " + team2score);
    gameUpdate(team1, team2, team1score, team2score); 

    if (team1score > team2score){
      System.out.println(team1.teamname + " is the winner!"); 
      
    }
    else if (team1score < team2score){
      System.out.println(team2.teamname + " is the winner!"); 
      
      
    }
    else if (team1score == team2score){
      if ( team1.starpower >= team2.starpower){
        System.out.println(team1.teamname + " is the winner!"); 
      if (team2.starpower < team1.starpower){
        System.out.println(team2.teamname + " is the winner!"); 
        
      }
      
          
          
        }
      }
    
    
    

      
      

       
  }

    

    
  
    
  // recursion merge sort methods
// using recursive mergesort helper method to sort my teams for the standings
    public static void mergeSort(Team[] elements) {
      int n = elements.length;
      Team[] temp = new Team[n]; // creating a new array for the objects to be added
      mergeSortHelper(elements, 0, n - 1, temp);
   }
  
// splits array into smaller lists of two 3x recursively then merges all the halves together in a sorted manner
   private static void mergeSortHelper(Team[] elements,int from, int end, Team[] temp) {
       if (from < end)
       {
          int middle = (from + end) / 2;
          mergeSortHelper(elements, from, middle, temp);
          mergeSortHelper(elements, middle + 1, end, temp);
          merge(elements, from, middle, end, temp);
       }
   }
// sorting of the elements and then the objects are put into a temporary array for swapping; here swaps occur based on the # of wins a team has
   private static void merge(Team[] elements, int from, int mid, int end, Team[] temp){
      int i = from;
      int j = mid + 1;
      int k = from;

      while (i <= mid && j <= end) {
         if (elements[i].wins > elements[j].wins){
            temp[k] = elements[i];
            i++;
         }
         else
         {
            temp[k] = elements[j];
            j++;
         }
         k++;
      }

      while (i <= mid)
      {
         temp[k] = elements[i];
         i++;
         k++;
      }

      while (j <= end)
      {
         temp[k] = elements[j];
         j++;
         k++;
      }

      for (k = from; k <= end; k++)
      {
         elements[k] = temp[k];
      }
   

  
    
    
  
    
  }

  // method that sorts standings based on the number of goals they let in
  public static void standingsbygoals(Team[] teams){
    for (int i = 0; i < 7; i++ ){
      if (teams[i].wins == teams[i+1].wins){
        // if equal number of wins but larger goal difference, swap spots
        if(teams[i].goaldifference > teams[i+1].goaldifference)
        {
        Team placeholder = teams[i]; 
          teams[i] = teams[i+1]; 
          teams[i+1] = placeholder; 
        }
      }
    }
  }
    





  
}