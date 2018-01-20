/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jerryCode;

/**
 * @author Zaq
 */
public class RobotState {
    final boolean 
            inflation,
            leverArm;
    
    public RobotState(boolean inflation, boolean leverArm){
        this.inflation = inflation;
        this.leverArm = leverArm;
    }
    
}
