package testdestroyer;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;

@SuppressWarnings("restriction")
public class Testers {
	private final Set<IMethod> methods;
	
	public Testers(Set<IMethod> methods) {
		this.methods = methods;
	}
	
	public Set<IMethod> getUntested() {
		Set<IMethod> untested = new HashSet<IMethod>();
		for (IMethod m : this.methods) {
			Set<IMethod> callers = getCallers(m);
			if (containsJUnit4TestMethod(callers)) {
				continue;
			}
			untested.add(m);
		}
		return untested;
	}
	
	private boolean containsJUnit4TestMethod(Set<IMethod> callers) {
		for (IMethod m : callers) {
			try {
				IAnnotation[] as = m.getAnnotations();
				for (IAnnotation a : as) {
					if (a.getElementName().equals("Test")) {
						return true;
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private Set<IMethod> getCallers(IMethod m) {
		 CallHierarchy callHierarchy = CallHierarchy.getDefault();
		 
		 IMember[] members = {m};
		 
		 MethodWrapper[] methodWrappers = callHierarchy.getCallerRoots(members);
		 Set<IMethod> callers = new HashSet<IMethod>();
		 
		 for (MethodWrapper mw : methodWrappers) {
		    MethodWrapper[] mw2 = mw.getCalls(new NullProgressMonitor());
		    Set<IMethod> temp = getIMethods(mw2);
		    callers.addAll(temp);    
		  }
		 
		return callers;
	}
	
	
	private Set<IMethod> getIMethods(MethodWrapper[] methodWrappers) {
		  Set<IMethod> c = new HashSet<IMethod>(); 
		  for (MethodWrapper m : methodWrappers) {
		   IMethod im = getIMethodFromMethodWrapper(m);
		   if (im != null) {
		    c.add(im);
		   }
		  }
		  return c;
	}

	private IMethod getIMethodFromMethodWrapper(MethodWrapper m) {
		  try {
		   IMember im = m.getMember();
		   if (im.getElementType() == IJavaElement.METHOD) {
		    return (IMethod)m.getMember();
		   }
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return null;
	}
}
