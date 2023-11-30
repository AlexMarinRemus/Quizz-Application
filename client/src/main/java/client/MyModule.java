/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import client.scenes.*;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import client.utils.IServerUtils;
import client.utils.ServerUtils;

//import client.scenes.AddQuoteCtrl;
import client.scenes.MainCtrl;
//import client.scenes.QuoteOverviewCtrl;

public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(SplashScreen.class).in(Scopes.SINGLETON);
        binder.bind(MultiplayerScreen.class).in(Scopes.SINGLETON);
        binder.bind(WaitingroomScene.class).in(Scopes.SINGLETON);
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(Question.class).in(Scopes.SINGLETON);
        binder.bind(IServerUtils.class).toInstance(new ServerUtils());
        binder.bind(SinglePlayer.class).in(Scopes.SINGLETON);
        binder.bind(IRandom.class).toInstance(new RandomNumber());
    }
}