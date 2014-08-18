package org.xtext.idea.build;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class XtextIdeaRefreshComponent {
	private final Object lock;
    private final Collection<String> filesToReferesh;
    
    public XtextIdeaRefreshComponent() {
    	lock = new Object();
		filesToReferesh = new HashSet<String>();
	}

    public void refresh(String path) {
        synchronized (lock) {
        	filesToReferesh.add(path);
        }
    }

    public Collection<String> getFilesToRefresh() {
        Set<String> result = new HashSet<String>();
        synchronized (lock) {
            result.addAll(filesToReferesh);
        }
        return result;
    }
}
