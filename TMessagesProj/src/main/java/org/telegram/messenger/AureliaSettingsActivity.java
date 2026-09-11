package org.telegram.messenger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;

public class AureliaSettingsActivity extends BaseFragment {
    
    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }
    
    @Override
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }
    
    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle("Aurelia功能");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) {
                    finishFragment();
                }
            }
        });
        
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(0xff0f0f0f);
        
        // 防撤回开关
        LinearLayout antiRevokeLayout = createSettingItem(context, "防撤回", "别人撤回的消息仍然可见，并显示\"已撤回\"", AureliaSettings.getInstance().isAntiRevokeEnabled(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newValue = !AureliaSettings.getInstance().isAntiRevokeEnabled();
                AureliaSettings.getInstance().setAntiRevokeEnabled(newValue);
                ((android.widget.Switch) v).setChecked(newValue);
            }
        });
        linearLayout.addView(antiRevokeLayout);
        
        // 下载加速开关
        LinearLayout downloadBoostLayout = createSettingItem(context, "下载加速", "无需Telegram会员，下载速度由你的网速决定", AureliaSettings.getInstance().isDownloadBoostEnabled(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newValue = !AureliaSettings.getInstance().isDownloadBoostEnabled();
                AureliaSettings.getInstance().setDownloadBoostEnabled(newValue);
                ((android.widget.Switch) v).setChecked(newValue);
            }
        });
        linearLayout.addView(downloadBoostLayout);
        
        // 加入我们按钮
        LinearLayout joinUsLayout = createButtonItem(context, "加入我们", "点击加入Aurelia官方频道", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Aurelia5200"));
                    intent.setPackage("org.telegram.messenger");
                    startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Aurelia5200"));
                    startActivity(intent);
                }
            }
        });
        linearLayout.addView(joinUsLayout);
        
        fragmentView = linearLayout;
        return fragmentView;
    }
    
    private LinearLayout createSettingItem(Context context, String title, String subtitle, boolean checked, View.OnClickListener listener) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(16), AndroidUtilities.dp(16), AndroidUtilities.dp(16));
        layout.setBackgroundResource(R.drawable.list_selector);
        
        LinearLayout textLayout = new LinearLayout(context);
        textLayout.setOrientation(LinearLayout.VERTICAL);
        textLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        
        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextColor(0xffffffff);
        titleView.setTextSize(16);
        textLayout.addView(titleView);
        
        TextView subtitleView = new TextView(context);
        subtitleView.setText(subtitle);
        subtitleView.setTextColor(0xff888888);
        subtitleView.setTextSize(13);
        textLayout.addView(subtitleView);
        
        layout.addView(textLayout);
        
        android.widget.Switch switchView = new android.widget.Switch(context);
        switchView.setChecked(checked);
        switchView.setOnClickListener(listener);
        layout.addView(switchView);
        
        return layout;
    }
    
    private LinearLayout createButtonItem(Context context, String title, String subtitle, View.OnClickListener listener) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(16), AndroidUtilities.dp(16), AndroidUtilities.dp(16));
        layout.setBackgroundResource(R.drawable.list_selector);
        layout.setOnClickListener(listener);
        
        LinearLayout textLayout = new LinearLayout(context);
        textLayout.setOrientation(LinearLayout.VERTICAL);
        textLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        
        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextColor(0xff5288c1);
        titleView.setTextSize(16);
        textLayout.addView(titleView);
        
        TextView subtitleView = new TextView(context);
        subtitleView.setText(subtitle);
        subtitleView.setTextColor(0xff888888);
        subtitleView.setTextSize(13);
        textLayout.addView(subtitleView);
        
        layout.addView(textLayout);
        
        return layout;
    }
}
