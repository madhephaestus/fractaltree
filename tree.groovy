import com.neuronrobotics.bowlerstudio.physics.TransformFactory
import com.neuronrobotics.sdk.addons.kinematics.math.RotationNR
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR

import eu.mihosoft.vrl.v3d.CSG
import eu.mihosoft.vrl.v3d.Cube
import eu.mihosoft.vrl.v3d.Transform

//Your code here
branchLength =  60

				
ArrayList<CSG> branches  = []

fractalDepth = 4
TransformNR brancLoc = new TransformNR()

void makeBranches(def branches, TransformNR brancLocation,int currentDepth, def y, def x,def z) {
	if(currentDepth==0)
		return
	def myLen = branchLength*(currentDepth)
	branch = new Cube(5,5,branchLength*(currentDepth-1)).toCSG()
	.toZMin()
	def left=brancLocation.times(
			new TransformNR(0,0,myLen,new RotationNR(Math.random()*y,Math.random()*z,Math.random()*x))
	)
	branches.add(branch.transformed(TransformFactory.nrToCSG(left)))

	def right=brancLocation.times(
		new TransformNR(0,0,myLen,new RotationNR(-Math.random()*y,Math.random()*z,-Math.random()*x))
	)
	branches.add(branch.transformed(TransformFactory.nrToCSG(right)))
	
	makeBranches(branches,left,currentDepth-1, y,x,z)
	makeBranches(branches,right,currentDepth-1, y,x,z)
}

makeBranches(branches,brancLoc,4, 20,20,60)

return branches