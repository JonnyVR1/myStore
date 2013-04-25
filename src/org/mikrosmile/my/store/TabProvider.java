package org.mikrosmile.my.store;

import com.htc.fragment.content.CarouselProvider;

@SuppressWarnings("deprecation")
public class TabProvider extends CarouselProvider {
	public TabProvider(){
        super();
        setupCarousel(MainActivity.AUTHORITY);
    }

} 
