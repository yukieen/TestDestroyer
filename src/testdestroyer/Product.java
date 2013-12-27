package testdestroyer;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class Product {

	private ICompilationUnit compilationUnit;
	
	Product(ICompilationUnit compilationUnit){
		this.compilationUnit = compilationUnit;
	}
	
	public Set<IMethod> getTestableMethods(){
		Set<IMethod> set = new HashSet<IMethod>();
		try {
			for (IType type : compilationUnit.getAllTypes()) {
				for (IMethod method : type.getMethods()) {
					if (Flags.AccPrivate == method.getFlags()) {
						continue;
					}
					set.add(method);
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return set;
	}
}
