package com.s2dwt.core.resourcemanager;

import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.files.FileHandleStream;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.leo.commons.utils.TNull;
import com.leo.commons.utils.TOldCompatibilityCode;
import com.s2dwt.addons.framebuffer.SwtFrameBuffer;
import com.s2dwt.addons.framebuffer.SwtFrameBufferBuilder;

public abstract class AResourceManager {
	// -------------------------------------------------------------------------------------------------------------------------
	private static Pixmap getPixmap(Color color) {
		Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGBA4444);
		pm1.setColor(color);
		pm1.fill();
		return pm1;
	}

	// -------------------------------------------------------------------------------------------------------------------------
	protected static FileHandle load(String pInternalPath) {

		String internalPath = pInternalPath.startsWith("/") ? pInternalPath.substring(1) : pInternalPath;

		FileHandle x = Gdx.files.classpath(internalPath);
		if (x != null) {
			boolean nb = x.exists();
			if (nb) {
				return x;
			}
		}

		if (TOldCompatibilityCode.TRUE) {
			return new FileHandle(internalPath) {
				@Override
				public boolean exists() {
					return super.exists();
				}

				@Override
				public InputStream read() {
					try {
						Class byc = Class.forName("de.heyho.gitlabapp.GitLabApp");
						InputStream jj = byc.getResourceAsStream("/" + internalPath);
						if (jj != null) {
							return jj;
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					InputStream input = FileHandle.class.getResourceAsStream("/" + file.getPath().replace('\\', '/'));
					if (input == null)
						throw new GdxRuntimeException("File not found: " + file + " (" + type + ")");
					return input;
				}

			};
		}

		FileHandleStream bb = new FileHandleStream(internalPath) {
			@Override
			public boolean exists() {
				return super.exists();
			}

			@Override
			public Reader reader() {
				// TODO Auto-generated method stub
				return super.reader();
			}

			@Override
			public InputStream read() {
				return super.read();
			}

			@Override
			public void writeBytes(byte[] bytes, boolean append) {
				// TODO Auto-generated method stub
				super.writeBytes(bytes, append);
			}
		};
		return bb;
		// return Gdx.files.local("assets/" + internalPath); //$NON-NLS-1$
		// throw new RuntimeException("file " + internalPath + " not found!");
	}

	// -------------------------------------------------------------------------------------------------------------------------
	private final static Texture _loadTexture(String internalPath) {
		return new Texture(load(internalPath));
	}

	// -------------------------------------------------------------------------------------------------------------------------
	protected final Map<String, Texture> loadedTextures = new HashMap<>();

	// -------------------------------------------------------------------------------------------------------------------------
	protected final Map<String, TextureRegion> loadedTextureRegions = new HashMap<>();

	// -------------------------------------------------------------------------------------------------------------------------
	// protected final Map<SpriteType, TextureRegion> resources = new
	// EnumMap<>(SpriteType.class);

	// -------------------------------------------------------------------------------------------------------------------------
	protected final Map<String, TextureRegion> virtualDirContents = new HashMap<>();

	// -------------------------------------------------------------------------------------------------------------------------
	private final Map<Integer, TextureRegion> colorTextureRegions = new HashMap<>();

	// -------------------------------------------------------------------------------------------------------------------------
	private Map<String, BitmapFont> scaledFonts = new HashMap<>();

//    // -------------------------------------------------------------------------------------------------------------------------
//    protected final void reggit(SpriteType pSpriteType, TextureRegion pTextureRegion) {
//        this.resources.put(pSpriteType, pTextureRegion);
//        this.virtualDirContents.put(pSpriteType.name(), pTextureRegion);
//    }
//
	// -------------------------------------------------------------------------------------------------------------------------
	public final TextureRegion getColorTextureRegion(@Nonnull Color pColor) {
		Integer bits = pColor.toIntBits();
		TextureRegion back = this.colorTextureRegions.get(bits);
		if (back == null) {
			back = new TextureRegion(new Texture(getPixmap(pColor)));
			this.colorTextureRegions.put(bits, back);
		}
		return back;
	}

	// -------------------------------------------------------------------------------------------------------------------------
	public final TextureRegion getColorTextureRegion(@Nonnull Color pColor, int pX, int pY) {
		Integer bits = pColor.toIntBits();
		TextureRegion back = new TextureRegion(new Texture(getPixmap(pColor)), pX, pY);
		this.colorTextureRegions.put(bits, back);
		return back;
	}

	// -------------------------------------------------------------------------------------------------------------------------
	public final Texture getTexture(String pResourceId) {
		return this.loadedTextures.computeIfAbsent(pResourceId, j -> {
			return _loadTexture(pResourceId);
		});
	}

	// -------------------------------------------------------------------------------------------------------------------------
	public final TextureRegion getTextureRegion(String pResourceId) {
		return this.loadedTextureRegions.computeIfAbsent(pResourceId, j -> {
			TextureRegion was = this.virtualDirContents.get(pResourceId);
			if (was != null)
				return was;
			int ak = pResourceId.indexOf("?"); // "objects/items/forEverDotsItems.png?32:11:14" //$NON-NLS-1$
			if (ak != -1) {
				String fi = pResourceId.substring(0, ak);
				String[] parts = pResourceId.substring(ak + 1).split(":"); //$NON-NLS-1$
				TextureRegion[][] texMap = loadTexMap(fi, Integer.parseInt(parts[0]));
				return texMap[Integer.parseInt(parts[1])][Integer.parseInt(parts[2])];
			}
			return new TextureRegion(getTexture(pResourceId));
		});
	}

	// -------------------------------------------------------------------------------------------------------------------------
	private final TextureRegion[][] fixBleeding(TextureRegion[][] pRegions) {
		for (TextureRegion[] array : pRegions) {
			for (TextureRegion region : array) {
				float fix = 0.01f;
				float x = region.getRegionX();
				float y = region.getRegionY();
				float width = region.getRegionWidth();
				float height = region.getRegionHeight();
				float invTexWidth = 1f / region.getTexture().getWidth();
				float invTexHeight = 1f / region.getTexture().getHeight();
				region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth,
						(y + height - fix) * invTexHeight);
			}
		}
		return pRegions;
	}

	// -------------------------------------------------------------------------------------------------------------------------
	protected final TextureRegion[][] loadTexMap(String pResourceId, int pSize) {
		return fixBleeding(TextureRegion.split(_loadTexture(pResourceId), pSize, pSize));
	}

	// -------------------------------------------------------------------------------------------------------------------------
//  public abstract BitmapFont getFont();

	// -------------------------------------------------------------------------------------------------------------------------
	public abstract Skin getSkin();

	// -------------------------------------------------------------------------------------------------------------------------
	private final Map<String, SwtFrameBuffer> frameBuffers = new HashMap<>();

	// -------------------------------------------------------------------------------------------------------------------------
	public final SwtFrameBuffer getFrameBuffer(String pFrameBufferId, int pWidth, int pHeight) {
		SwtFrameBuffer fb = this.frameBuffers.get(pFrameBufferId);
		if (fb == null) {
			fb = new SwtFrameBufferBuilder(Pixmap.Format.RGBA4444, pWidth, pHeight).build();
			this.frameBuffers.put(pFrameBufferId, fb);
		}
		return fb;
	}

	// -------------------------------------------------------------------------------------------------------------------------
	public final SwtFrameBuffer getFrameBuffer(String pFrameBufferId) {
		return this.frameBuffers.get(pFrameBufferId);
	}

	// -------------------------------------------------------------------------------------------------------------------------
	@Nonnull
	public abstract Set<ResourceLoader> getLoaders();

	// -------------------------------------------------------------------------------------------------------------------------
	@Nonnull
	public final BitmapFont getFont(float scale, boolean useInt) {
		// scaledFonts.clear();
		return TNull.checkNull(this.scaledFonts.computeIfAbsent((useInt ? "1" : "0") + ":" + scale, s -> {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(load("fonts/Minecraftia.ttf"));
			// FreeTypeFontGenerator generator = new
			// FreeTypeFontGenerator(load("ui/font/fixedsys.ttf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameter.size = 16;
			parameter.borderWidth = 2f;
			parameter.borderColor = Color.DARK_GRAY;
			BitmapFont back = generator.generateFont(parameter);
			// back.getData().setScale(1f);
			back.getData().setScale(scale);
			back.setUseIntegerPositions(useInt);
			back.setColor(Color.WHITE);
			return back;
		}));
	}

	// -------------------------------------------------------------------------------------------------------------------------
}
