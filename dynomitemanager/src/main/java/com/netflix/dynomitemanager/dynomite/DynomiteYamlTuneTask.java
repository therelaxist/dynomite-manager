/**
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netflix.dynomitemanager.dynomite;

import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.netflix.dynomitemanager.defaultimpl.IConfiguration;
import com.netflix.dynomitemanager.sidecore.scheduler.SimpleTimer;
import com.netflix.dynomitemanager.sidecore.scheduler.Task;
import com.netflix.dynomitemanager.sidecore.scheduler.TaskTimer;
import com.netflix.dynomitemanager.sidecore.utils.ProcessTuner;

/**
 * Tune a process by writing a YAML configuration file.
 */
@Singleton
public class DynomiteYamlTuneTask extends Task {

	public static final String JOBNAME = "Tune-Task";
	private final ProcessTuner tuner;

	@Inject
	public DynomiteYamlTuneTask(IConfiguration config, ProcessTuner tuner) {
		super(config);
		this.tuner = tuner;
	}

	public void execute() throws IOException {
		tuner.writeAllProperties(config.getDynomiteYaml());
	}

	@Override
	public String getName() {
		return "Tune-Task";
	}

	// update the YML every 60 seconds.
	public static TaskTimer getTimer() {
		return new SimpleTimer(JOBNAME, 60L * 1000);
	}

}
