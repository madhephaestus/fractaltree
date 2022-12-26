import com.neuronrobotics.bowlerstudio.physics.TransformFactory
import com.neuronrobotics.sdk.addons.kinematics.math.RotationNR
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR

import eu.mihosoft.vrl.v3d.CSG
import eu.mihosoft.vrl.v3d.Cube
import eu.mihosoft.vrl.v3d.Transform
branchLength =  60
ArrayList<CSG> branches  = []
fractalDepth = 4
TransformNR brancLoc = new TransformNR()

CSG leif(def branches,TransformNR brancLocation) {
	CSG branch = new Cube(20,10,5).toCSG()
	.toZMin().toXMin()
	CSG flower = branch
	for(int i=0;i<360;i+=(360/5)) {
		flower= flower.union(branch.rotz(i))
	}
	def tip=brancLocation.times(
		new TransformNR(0,0,branchLength,new RotationNR()))
	return flower.transformed(TransformFactory.nrToCSG(tip))
}

void makeBranches(def branches, TransformNR brancLocation,int currentDepth, def y, def x,def z) {
	if(currentDepth==1) {
		CSG flower = leif( branches, brancLocation)
		for(int i=0;i<branches.size();i++) {
			if(flower.touching(branches.get(i))) {
				return;
			}
		}
		branches.add(flower)
		return
	}
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
makeBranches(branches,brancLoc,5, 30,20,60)
return branches