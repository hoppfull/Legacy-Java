package src.utilities.mathematics;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class mat4{
	private double[][] M;
	
	public mat4(){
		M = new double[4][4];
	}
	
	public void setM(int n, int m, double value){
		M[n][m] = value;
	}
	
	public double getM(int n, int m){
		return M[n][m];
	}
	
	public void setIdentity(){
		M[0][0] = 1; M[0][1] = 0; M[0][2] = 0; M[0][3] = 0;
		M[1][0] = 0; M[1][1] = 1; M[1][2] = 0; M[1][3] = 0;
		M[2][0] = 0; M[2][1] = 0; M[2][2] = 1; M[2][3] = 0;
		M[3][0] = 0; M[3][1] = 0; M[3][2] = 0; M[3][3] = 1;
	}
	
	public void setScale(double x, double y, double z){
		M[0][0] = x; M[0][1] = 0; M[0][2] = 0; M[0][3] = 0;
		M[1][0] = 0; M[1][1] = y; M[1][2] = 0; M[1][3] = 0;
		M[2][0] = 0; M[2][1] = 0; M[2][2] = z; M[2][3] = 0;
		M[3][0] = 0; M[3][1] = 0; M[3][2] = 0; M[3][3] = 1;
	}
	
	public void setRotation(vec3 i, double turns){
		double sin = Math.sin(turns * 6.28318530718);
		double cos = Math.cos(turns * 6.28318530718);
		double a = i.getX(); double b = i.getY(); double c = i.getZ(); //Rotation parameters
		
			//Row 0:
			M[0][0] = ( a*a*(1 - cos) +   cos );
			M[0][1] = ( b*a*(1 - cos) - c*sin );
			M[0][2] = ( c*a*(1 - cos) + b*sin );
			M[0][3] = 0;
			
			//Row 1:
			M[1][0] = ( a*b*(1 - cos) + c*sin );
			M[1][1] = ( b*b*(1 - cos) +   cos );
			M[1][2] = ( c*b*(1 - cos) - a*sin );
			M[1][3] = 0;
			
			//Row 2:
			M[2][0] = ( a*c*(1 - cos) - b*sin );
			M[2][1] = ( b*c*(1 - cos) + a*sin );
			M[2][2] = ( c*c*(1 - cos) +   cos );
			M[2][3] = 0;
			
			//Row 3:
			M[3][0] = 0; M[3][1] = 0; M[3][2] = 0; M[3][3] = 1;
	}
	
	public void setTranslation(vec3 i){
		M[0][0] = 1; M[0][1] = 0; M[0][2] = 0; M[0][3] = i.getX();
		M[1][0] = 0; M[1][1] = 1; M[1][2] = 0; M[1][3] = i.getY();
		M[2][0] = 0; M[2][1] = 0; M[2][2] = 1; M[2][3] = i.getZ();
		M[3][0] = 0; M[3][1] = 0; M[3][2] = 0; M[3][3] = 1;
	}
	
	public void setProjection(int width, int height, double zNear, double zFar, double fov){
		if(fov >= 1) fov = 0.99; //Limit to 0.99 or we get infinity error at 1.0. >1.0 will give strange result.
		double tanHalfFOV = Math.tan(fov * 3.14159265359 / 2);
		double zRange = zNear - zFar;
		//Row 0:
		M[0][0] = height / (tanHalfFOV * width);
		M[0][1] = 0;
		M[0][2] = 0;
		M[0][3] = 0;
		
		//Row 1:
		M[1][0] = 0;
		M[1][1] = 1.0 / (tanHalfFOV);
		M[1][2] = 0;
		M[1][3] = 0;
		
		//Row 2:
		M[2][0] = 0;
		M[2][1] = 0;
		M[2][2] = (-zNear - zFar)/zRange;
		M[2][3] = 2 * zFar * zNear / zRange;
		
		//Row 3:
		M[3][0] = 0; M[3][1] = 0; M[3][2] = 1; M[3][3] = 0;
	}
	
	public FloatBuffer getFloatBuffer(){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		for(int n = 0; n < 4; n++){
			for(int m = 0; m < 4; m++){
				buffer.put( (float)(M[n][m]) );
			}
		}
		buffer.flip(); //Flip from writemode to readmode
		return buffer;
	}
}