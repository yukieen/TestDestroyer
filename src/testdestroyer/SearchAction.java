package testdestroyer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
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
		IWorkbench workbench = PlatformUI.getWorkbench();
		MessageDialog.openInformation(
				workbench.getActiveWorkbenchWindow().getShell(),
				"TestDestory",
				"成功！");
		IJavaElement je = JavaUI.getEditorInputJavaElement(this.targetEditer.getEditorInput());
		ICompilationUnit c = (ICompilationUnit)je;
		StringBuilder sb = new StringBuilder();
		try {
			for (IType type : c.getAllTypes()) {
				for (IMethod method : type.getMethods()) {
					if (Flags.AccPrivate == method.getFlags()) {
						continue;
					}
					sb.append(method.getElementName());
				}
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MessageDialog.openInformation(
				workbench.getActiveWorkbenchWindow().getShell(),
				"TestDestory",
				sb.toString());
		
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
