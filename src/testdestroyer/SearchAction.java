package testdestroyer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class SearchAction implements IEditorActionDelegate {

	private IEditorPart targetEditer;
	
	@Override
	public void run(IAction action) {
		IJavaElement je = JavaUI.getEditorInputJavaElement(this.targetEditer.getEditorInput());
		Product product = new Product((ICompilationUnit)je);
		List<IMethod> testableMethods = product.getTestableMethods();
		
		StringBuilder sb = new StringBuilder();
		for(IMethod method : testableMethods){
			sb.append(method.getElementName() + " = ");
			HashSet<IMethod> callerMethods = getCallersOf(method);
			sb.append(callerMethods.toString());
			sb.append(System.getProperty("line.separator"));
		}
		
		IWorkbench workbench = PlatformUI.getWorkbench();
		MessageDialog.openInformation(
				workbench.getActiveWorkbenchWindow().getShell(),
				"TestDestory",
				sb.toString());
		
	}
	
	public HashSet<IMethod> getCallersOf(IMethod m) {
		 CallHierarchy callHierarchy = CallHierarchy.getDefault();
		 
		 IMember[] members = {m};
		 
		 MethodWrapper[] methodWrappers = callHierarchy.getCallerRoots(members);
		 HashSet<IMethod> callers = new HashSet<IMethod>();
		 
		 for (MethodWrapper mw : methodWrappers) {
		    MethodWrapper[] mw2 = mw.getCalls(new NullProgressMonitor());
		    HashSet<IMethod> temp = getIMethods(mw2);
		    callers.addAll(temp);    
		  }
		 
		return callers;
	}
	
	HashSet<IMethod> getIMethods(MethodWrapper[] methodWrappers) {
		  HashSet<IMethod> c = new HashSet<IMethod>(); 
		  for (MethodWrapper m : methodWrappers) {
		   IMethod im = getIMethodFromMethodWrapper(m);
		   if (im != null) {
		    c.add(im);
		   }
		  }
		  return c;
		 }
		 
	IMethod getIMethodFromMethodWrapper(MethodWrapper m) {
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
		 
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.targetEditer = targetEditor;
	}

}
