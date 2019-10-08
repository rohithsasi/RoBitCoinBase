A breif summary of the architecture and some explanations of my choices:

-The ui layer talk to the presentation layer(view model) asking for the data it needs. The viewmodel makes the api call to
the apps data repository. The only reference ViewModel keeps is that of the  data repo. VM is completely de coupled from the ui.

-The data repo layer is responsible for calling into the api layer which inturn makes the network requests using retrofit.

-The network req is made in a background thread with rx java disposable and returns in a observable which is subscribed upon in the main thread

- An intersting thing to note here is that  I bubbled up data objects from the rx network layer with the help of result listeners(callback).
  .Once the repos get the result from the network result listeners it caches(more explanation in the javadocs) the data in an IO thread and informs the presenter(VM)
  via anoter result listener. Now the presentaion layer presents(emits) the fully proccesed data to the all the Ui(Frag and Activities) view a event bus.

- I did not bubble back result observable all the way to presenter and used listeners because since lately with kotlin I have been using native result listeners
  for callback and was managing threading ourselves using executors. With the advent of co routines we started to moving to co routines
  for threading. Also I am familiar with this architecture and I could write this up really quick and debug easily.

- If I had more time I would have use rx way more. I am conisderably decent with rx. It just I need some brushing up to do.


             Even bus emmission             <-listners of parsed ui data      <-listneres of raw data       <-Observables(raw network response)
          -----------<--------------------<--------------------------------<-----------------------------------------------<
         /   /       /                    |                                |                                               |
        /   /       /                     |                                |                                               |
       /   /       /                      |                                |                                               |
  View1...View2...View3    ---------->ViewModel---------------------------> Api---------------------------------->RetrofitServiceApi
    (Frag/Activites)                (presentation)