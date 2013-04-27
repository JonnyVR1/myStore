package org.mikrosmile.my.store;

import android.os.Bundle;

import com.htc.fragment.widget.CarouselFragment;
import com.htc.fragment.widget.CarouselHost;
import com.htc.fragment.widget.CarouselTabSpec;

public class Carousel extends CarouselFragment {

	public Carousel() {
		super(MainActivity.AUTHORITY);
		requestCarouselFeature(CarouselFragment.FEATURE_CUSTOM_TITLE);
	}
	
	private void addTab1(CarouselHost host, String tag, int icon, int str) {
        host.addTab(getActivity(), new CarouselTabSpec(tag,
                str, icon, icon, icon, About.class.getName()));
    }
	
	private void addTab2(CarouselHost host, String tag, int icon, int str) {
        host.addTab(getActivity(), new CarouselTabSpec(tag,
                str, icon, icon, icon, NewsFragment.class.getName()));
    }
	private void addTab3(CarouselHost host, String tag, int icon, int str) {
        host.addTab(getActivity(), new CarouselTabSpec(tag,
                str, icon, icon, icon, Skins.class.getName()));
    }
	private void addTab4(CarouselHost host, String tag, int icon, int str) {
        host.addTab(getActivity(), new CarouselTabSpec(tag,
                str, icon, icon, icon, Battery.class.getName()));
    }
	private void addTab5(CarouselHost host, String tag, int icon, int str) {
        host.addTab(getActivity(), new CarouselTabSpec(tag,
                str, icon, icon, icon, Keyboard.class.getName()));
    }
	private void addTab6(CarouselHost host, String tag, int icon, int str) {
        host.addTab(getActivity(), new CarouselTabSpec(tag,
                str, icon, icon, icon, SystemMods.class.getName()));
        
    }
	private void addTab7(CarouselHost host, String tag, int icon, int str) {
        host.addTab(getActivity(), new CarouselTabSpec(tag,
                str, icon, icon, icon, Kernels.class.getName()));
        
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final CarouselHost host = getCarouselHost();

        addTab1(host, "Tab1", R.drawable.rom_logo,
                R.string.app_name);
        addTab2(host, "Tab2", R.drawable.ic_news,
                R.string.news);
        addTab3(host, "Tab3", R.drawable.ic_skins,
                R.string.skins);
        addTab4(host, "Tab4", R.drawable.ic_sysui,
                R.string.battery);
        addTab5(host, "Tab5", R.drawable.ic_kb,
                R.string.keyboard);
        addTab6(host, "Tab6", R.drawable.ic_system,
                R.string.mods);
        addTab7(host, "Tab7", R.drawable.ic_kernels,
                R.string.kernels);
    }
	public void enterCarouselEditMode() {
		    enterEditMode();
		  }

}
