package cn.youmay;


import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Adapter extends SimpleAdapter {
	private int[] mTo;
    private String[] mFrom;
    private ViewBinder mViewBinder;
    private List<? extends Map<String, ?>> mData;
    private int mResource;
    private LayoutInflater mInflater;
	public Adapter(Context context,List<? extends Map<String, ?>> data, int resource, String[] from,int[] to) {
		super(context, data, resource, from, to);
		mData = data;
        mResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
 
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mResource);
    }
    private View createViewFromResource(int position, View convertView,
            ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);
 
            final int[] to = mTo;
            final int count = to.length;
            final View[] holder = new View[count];
 
            for (int i = 0; i < count; i++) {
                holder[i] = v.findViewById(to[i]);
            }
            v.setTag(holder);
        } else {
            v = convertView;
        }
        bindView(position, v);
        return v;
    }
 
    private void bindView(int position, View view) {
        final Map dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }
 
        final ViewBinder binder = mViewBinder;
        final View[] holder = (View[]) view.getTag();
        final String[] from = mFrom;
        final int[] to = mTo;
        final int count = to.length;
 
        for (int i = 0; i < count; i++) {
            final View v = holder[i];
            if (v != null) {
                final Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }
 
                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, data, text);
                }
 
                if (!bound) {
                	//�Զ������������ؼ��������ݴ������Ŀؼ������Լ�ֵ��������ͣ�ִ����Ӧ�ķ���
                	//���Ը���Լ���Ҫ�������if��䡣��CheckBox�ȼ̳���TextView�Ŀؼ�Ҳ�ᱻʶ���TextView�� �����Ҫ�ж�ֵ��������
                  if (v instanceof TextView) {
                	  //�����TextView�ؼ�
                        setViewText((TextView) v, text);
                        //����SimpleAdapter�Դ�ķ����������ı�
                    } else if (v instanceof ImageView) {//�����ImageView�ؼ�
                        setViewImage((ImageView) v, (Drawable) data); 
                        //���������Լ�д�ķ���������ͼƬ
                    } else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                " view that can be bounds by this SimpleAdapter");
                    }
                }
            }
        }
    }
 
    public void setViewImage(ImageView v, Drawable value) {
    	v.setImageDrawable(value);

    }
 
};