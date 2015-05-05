/**
 * @author Richard Shepard
 * @version May 3, 2015
 */
package com.rshepard.littleleagueapp;

/**
 * This Class defines a single baseball team.
 * Fields include: name, outs, strikes, balls, and runs
 */
public class Team {
	
	private String name;
	private int outs;
	private int strikes;
	private int balls;
	private int runs;
	
	/**
	 * Creates a new Team object,
	 * sets the name, and initializes all
	 * other fields to zero
	 * @param name String value of the name of the team
	 */
	public Team(String name) {
		this.name = name;
		outs = 0;
		strikes = 0;
		balls = 0;
		runs = 0;
	}

	/**
	 * Gets the current outs of the Team
	 * @return Integer value of the outs
	 */
	public int getOuts() {
		return outs;
	}

	/**
	 * Sets the outs for the Team
	 * @param outs Integer of the outs to be set
	 */
	public void setOuts(int outs) {
		this.outs = outs;
	}

	/**
	 * Gets the runs of the Team
	 * @return Integer value of the runs
	 */
	public int getRuns() {
		return runs;
	}

	/**
	 * Sets the runs for the Team
	 * @param Integer of the runs to be set
	 */
	public void setRuns(int runs) {
		this.runs = runs;
	}

	/**
	 * Gets the name of the Team
	 * @return String value of the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the current amount of strikes
	 * @return Integer value of the strikes
	 */
	public int getStrikes() {
		return strikes;
	}

	/**
	 * Sets the value of the strikes
	 * @param strikes Integer value of the strikes
	 */
	public void setStrikes(int strikes) {
		this.strikes = strikes;
	}

	/**
	 * Gets the value of the balls
	 * @return Integer value of the balls
	 */
	public int getBalls() {
		return balls;
	}

	/**
	 * Sets the value of the balls
	 * @param balls Integer value of the balls to be set
	 */
	public void setBalls(int balls) {
		this.balls = balls;
	}
}
