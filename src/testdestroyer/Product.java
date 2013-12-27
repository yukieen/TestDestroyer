package testdestroyer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class Product {
	/**
	 * プロダクトクラス
	 */
	private ICompilationUnit compilationUnit;
	
	Product(ICompilationUnit compilationUnit){
		this.compilationUnit = compilationUnit;
	}
	
	public List<IMethod> getTestableMethods(){
		List<IMethod> list = new ArrayList<IMethod>();
		try {
			for (IType type : compilationUnit.getAllTypes()) {
				for (IMethod method : type.getMethods()) {
					if (Flags.AccPrivate == method.getFlags()) {
						continue;
					}
					list.add(method);
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return list;
	}
}
