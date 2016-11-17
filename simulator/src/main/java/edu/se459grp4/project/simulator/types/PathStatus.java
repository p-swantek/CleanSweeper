/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.se459grp4.project.simulator.types;

/**
 * Data type that represents if a path in the simulation is able to be traversed.  A path can
 * be open, or it can be blocked by some obstacle or is a staircase.  Unexplored paths are
 * unknown
 *
 * @author Group 4
 * @version 1.8
 * 
 */
 public enum PathStatus
 {
        UNKNOWN ,
        Open ,
        Blocked ,
        Stair ,
};